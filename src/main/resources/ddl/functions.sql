create function lastid(tbl text) returns integer
  language sql
  immutable
  returns null on null input
  return currval(tbl || '_id_seq');


create function rdnid() returns integer 
  language sql
  immutable
  returns null on null input
  -- max int
  return random() * (2147483646) + 1;

create function setid(tbl text, id integer) returns integer
  language sql
  immutable
  returns null on null input
  return setval(tbl || '_id_seq', id, true);
