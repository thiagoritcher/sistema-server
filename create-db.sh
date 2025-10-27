sudo -u postgres createuser $1 --pwprompt
sudo -u postgres createdb -O $1 $1