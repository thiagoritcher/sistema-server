sudo service postgresql restart 
sudo -u postgres dropdb $1
sudo -u postgres dropuser $1
sudo -u postgres createuser $1 --pwprompt
sudo -u postgres createdb -O $1 $1