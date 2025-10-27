package br.com.ritcher.sistema;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.CaseUtils;

/**
 * Run with 
 * java br.com.ritcher.sistema.CreateLayers snake_case_name package-path package-name table-package
 * Eg:
 * 
 * java br.com.ritcher.sistema.CreateLayers user_details seguranca /home/user/projects/sistemas/br/com/ritcher/sistemas br.com.ritcher.sistemas seg username:String,password:String
 */

public class CreateLayers {

  public static void main(String[] args) {
    try {
      new CreateLayers().execute(args);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String packageName;
  private String tablePackage;
 
  static class Property {
	  String name;
	  String type;

	public Property(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
  }
  

  public void execute(String[] args) throws IOException {
    int i = 0;

    String name = args[i++];
    String module = args[i++];
    String path = args[i++];
    packageName = args[i++];
    tablePackage = args[i++];
    List<Property> properties = Arrays.asList(args[i++].trim().split(",")).stream().map(p -> p.split(":")).map(a -> new Property(a[0], a[1])).toList();
    

    String uname = snakeToCamelUpper(name);
    
   
    Property findBy = properties.stream().filter(p -> "String".equals(p.type)).findFirst().get();

    write(path + "/"+ module + "/controller/" + uname + "Controller.java", getController(name, module));
    write(path + "/"+ module  + "/data/"+ "/" + uname + ".java", getModel(name, properties, module));
    write(path + "/"+ module + "/repository/"+ uname + "Repository.java", getRepository(name, module, findBy));
    write(path + "/"+ module + "/service/"+  uname + "Service.java", getService(name, properties,findBy, module));
  }

  public void write(String file, String contents) throws IOException {
    File file2 = new File(file);
    if (!file2.getParentFile().exists()) {
      file2.getParentFile().mkdirs();
    }
    FileOutputStream fos = new FileOutputStream(file2);
    fos.write(contents.getBytes());
    fos.close();
  }

  public void print(String file, String contents) throws IOException {
    System.out.println("=======================");
    System.out.println(file);
    System.out.println(contents);
  }
  
  static class PropertyTransform {
	  Property property;
	  String transform;

	public PropertyTransform(Property property, String transform) {
		super();
		this.property = property;
		this.transform = transform;
	}
	
	public PropertyTransform dotransform(String transform) {
		this.transform = transform;
		return this;
	}
  }

  private String getService(String name, List<Property> properties, Property findBy, String module) {
    String uname = snakeToCamelUpper(name);
    String pname = snakeToCamelUpper(findBy.name);

    return "package " + packageName +"."+ module+ ".service;\n"
        + "\n"
        + "import org.springframework.beans.factory.annotation.Autowired;\n"
        + "import org.springframework.stereotype.Service;\n"
        + "import org.springframework.data.domain.PageRequest;\n"
        + "import org.springframework.transaction.annotation.Transactional;\n"
        + "\n"
        + "import " + packageName +"."+ module+ ".data." + uname + ";\n"
        + "import " + packageName +"."+ module+ ".repository." + uname + "Repository;\n"
        + "\n"
        + "import reactor.core.publisher.Flux;\n"
        + "import reactor.core.publisher.Mono;\n"
        + "\n"
        + "@Service\n"
        + "@Transactional\n"
        + "public class " + uname + "Service {\n"
        + "\n"
        + "	@Autowired\n"
        + "	" + uname + "Repository " + name + "Repository;\n"
        + "\n"
        + "	public <S extends " + uname + "> Mono<S> save(S entity) {\n"
        + "		return " + name + "Repository.save(entity);\n"
        + "	}\n"
        + "\n"
        + "	public Mono<" + uname + "> findById(Long id) {\n"
        + "		return " + name + "Repository.findById(id);\n"
        + "	}\n"
        + "\n"
        + "	public Flux<" + uname + "> findAll() {\n"
        + "		return " + name + "Repository.findAll();\n"
        + "	}\n"
        + "\n"
        + "	public Mono<" + uname + "> delete" + uname + "(Long " + name + "Id){\n"
        + "        return " + name + "Repository.findById(" + name + "Id)\n"
        + "                .flatMap(existing" + uname + " -> " + name + "Repository.delete(existing" + uname + ")\n"
        + "                .then(Mono.just(existing" + uname + ")));\n"
        + "    }\n"
        + "	\n"
        + "    public Mono<" + uname + "> update" + uname + "(Long " + name + "Id,  " + uname + " " + name + "){\n"
        + "        return " + name + "Repository.findById(" + name + "Id)\n"
        + "                .flatMap(db" + uname + " -> {\n" + 
        
		  properties.stream()
            .map(p -> new PropertyTransform(p, p.name.endsWith("_id") ? p.name.substring(0, p.name.length() - 3) : p.name))
            .map(p -> p.dotransform(snakeToCamelUpper(p.transform)))
            .map(p -> "                    db" + uname + ".set" + p.transform + "(" + name  + ("Boolean".equals(p.property.type) ? ".is" : ".get" ) + p.transform + "());\n")
            .collect(Collectors.joining())
        + "                    return " + name + "Repository.save(db" + uname + ");\n"
        + "                });\n"
        + "    }	\n"
        + "    \n"
        + "    public Flux<" + uname + "> findAllQuery(String query,  PageRequest page){\n"
        + "        return " + name + "Repository.findBy"+pname+"ContainsAllIgnoringCaseOrderBy"+pname+"(query, page);\n"
        + "    }\n"
        + "}\n";
  }

  private String getRepository(String name, String module, Property findBy) {
    String uname = snakeToCamelUpper(name);
    String pname = snakeToCamelUpper(findBy.name);
    return "package " + packageName +"."+ module+ ".repository;\n"
        + "\n"
        + "import reactor.core.publisher.Flux;\n"
        + "import org.springframework.data.domain.Pageable;\n"
        + "import org.springframework.data.repository.reactive.ReactiveCrudRepository;\n"
        + "import org.springframework.data.repository.reactive.ReactiveSortingRepository;\n"
        + "\n"
        + "import " + packageName +"."+ module+ ".data." + uname + ";\n"
        + "\n"
        + "public interface " + uname + "Repository \n"
        + "					extends ReactiveCrudRepository<" + uname + ", Long>, ReactiveSortingRepository<" + uname
        + ", Long>{\n"
        + "	Flux<" + uname + "> findBy"+pname+"ContainsAllIgnoringCaseOrderBy"+pname+"(String query, Pageable pages);\n"
        + "\n"
        + "}\n"
        + "";

  }

  private String getModel(String name, List<Property> properties, String module) {
    String uname = snakeToCamelUpper(name);
    StringBuffer ps = new StringBuffer();
	ps.append(
		"	@Column(\"" + "id" + "\")\n" +
			"	private Long id;\n");
    for (Property p : properties) {
      if (p.name.endsWith("_id")) {
        ps.append(
            "	@Column(\"" + p.name + "\")\n" +
                "	private Long " + snakeToCamel(p.name.substring(0, p.name.length() - 3)) + ";\n");
      } else {
        if (!p.equals(snakeToCamel(p.name))) {
          ps.append(
              "	@Column(\"" + p.name + "\")\n");
        }
        ps.append(
            "	private "+p.type+" " + snakeToCamel(p.name) + ";\n");
      }
    }

    return "package " + packageName +"."+ module + ".data;\n"
        + "\n"
        + "import org.springframework.data.annotation.Id;\n"
        + "import org.springframework.data.relational.core.mapping.Table;\n"
        + "import org.springframework.data.relational.core.mapping.Column;\n"
        + "\n"
        + "import lombok.AllArgsConstructor;\n"
        + "import lombok.Data;\n"
        + "import lombok.NoArgsConstructor;\n"
        + "\n"
        + "@Data\n"
        + "@AllArgsConstructor\n"
        + "@NoArgsConstructor\n"
        + "@Table(\"" + tablePackage + "_" + name + "\")\n"
        + "public class " + uname + " {\n"
        + "	@Id\n"
        + ps
        + "}\n";
  }

  private String snakeToCamelUpper(String s) {
    return CaseUtils.toCamelCase(s, true, '_');
  }

  private String snakeToCamel(String s) {
    return CaseUtils.toCamelCase(s, false, '_');
  }

  private String getController(String sname, String module) {
    String lname = sname.toLowerCase();
    String upperName = snakeToCamelUpper(sname);
    String name = snakeToCamel(sname);
    String result = "package " + packageName +"."+ module +".controller;\n"
        + "\n"
        + "import org.springframework.data.domain.PageRequest;\n"
        + "import org.springframework.beans.factory.annotation.Autowired;\n"
        + "import org.springframework.http.HttpStatus;\n"
        + "import org.springframework.http.ResponseEntity;\n"
        + "import org.springframework.web.bind.annotation.DeleteMapping;\n"
        + "import org.springframework.web.bind.annotation.GetMapping;\n"
        + "import org.springframework.web.bind.annotation.PathVariable;\n"
        + "import org.springframework.web.bind.annotation.PostMapping;\n"
        + "import org.springframework.web.bind.annotation.PutMapping;\n"
        + "import org.springframework.web.bind.annotation.RequestBody;\n"
        + "import org.springframework.web.bind.annotation.RequestMapping;\n"
        + "import org.springframework.web.bind.annotation.ResponseStatus;\n"
        + "import org.springframework.web.bind.annotation.RestController;\n"
        + "\n"
        + "import " + packageName + ".lib.PostQuery;\n"
        + "import " + packageName  +"."+ module + ".data." + upperName + ";\n"
        + "import " + packageName  +"."+ module + ".service." + upperName + "Service;\n"
        + "\n"
        + "import reactor.core.publisher.Flux;\n"
        + "import reactor.core.publisher.Mono;\n"
        + "\n"
        + "@RestController\n"
        + "@RequestMapping(\"/" + lname + "\")\n"
        + "public class " + upperName + "Controller {\n"
        + "\n"
        + "	@Autowired\n"
        + "	private " + upperName + "Service " + name + "Service;\n"
        + "\n"
        + "	@PostMapping\n"
        + "	@ResponseStatus(HttpStatus.CREATED)\n"
        + "	public Mono<" + upperName + "> create(@RequestBody " + upperName + " " + name + ") {\n"
        + "		return " + name + "Service.save(" + name + ");\n"
        + "	}\n"
        + "\n"
        + "	@GetMapping\n"
        + "	public Flux<" + upperName + "> getAll" + upperName + "s(){\n"
        + "		return " + name + "Service.findAll();\n"
        + "	}\n"
        + "\n"
        + "	@PostMapping(\"/query\")\n"
        + "	public Flux<" + upperName + "> getAll" + upperName + "Query(@RequestBody PostQuery query){\n"
        + "		return " + name + "Service.findAllQuery(query.getQuery(), PageRequest.of(0, 50));\n"
        + "	}\n"
        + ""
        + "\n"
        + "	@GetMapping(\"/{" + name + "Id}\")\n"
        + "	 public Mono<ResponseEntity<" + upperName + ">> get" + upperName + "ById(@PathVariable Long " + name
        + "Id){\n"
        + "        Mono<" + upperName + "> " + name + " = " + name + "Service.findById(" + name + "Id);\n"
        + "        return " + name + ".map(u -> ResponseEntity.ok(u))\n"
        + "                .defaultIfEmpty(ResponseEntity.notFound().build());\n"
        + "    }\n"
        + "	\n"
        + "    @PutMapping(\"/{" + name + "Id}\")\n"
        + "    public Mono<ResponseEntity<" + upperName + ">> update" + upperName + "ById(@PathVariable Long " + name
        + "Id, @RequestBody " + upperName + " " + name + "){\n"
        + "        return " + name + "Service.update" + upperName + "(" + name + "Id," + name + ")\n"
        + "                .map(updated" + upperName + " -> ResponseEntity.ok(updated" + upperName + "))\n"
        + "                .defaultIfEmpty(ResponseEntity.badRequest().build());\n"
        + "    }\n"
        + "    \n"
        + "    \n"
        + "    @DeleteMapping(\"/{" + name + "Id}\")\n"
        + "    public Mono<ResponseEntity<Void>> delete" + upperName + "ById(@PathVariable Long " + name + "Id){\n"
        + "        return " + name + "Service.delete" + upperName + "(" + name + "Id)\n"
        + "                .map( r -> ResponseEntity.ok().<Void>build())\n"
        + "                .defaultIfEmpty(ResponseEntity.notFound().build());\n"
        + "    }\n"
        + "}";

    return result;
  }
}