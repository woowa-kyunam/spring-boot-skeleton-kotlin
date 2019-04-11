FROM mysql:5.7

ENV MYSQL_ROOT_PASSWORD root
ENV MYSQL_DATABASE skeleton
ENV MYSQL_USER local
ENV MYSQL_PASSWORD local

RUN rm -rf /etc/mysql/my.cnf

RUN echo '[client] \n\
default-character-set=utf8 \n\n\
[mysql] \n\
default-character-set=utf8 \n\n\
[mysqld] \n\
collation-server = utf8_unicode_ci \n\
init-connect='SET NAMES utf8' \n\
character-set-server = utf8 \n\n\
!includedir /etc/mysql/conf.d/ \n\
!includedir /etc/mysql/mysql.conf.d/' >> /etc/mysql/my.cnf

EXPOSE 3306
