#---------------------------------------------------------#
#                                                         #
# Stage 1: Setup Postgres Database                        #
#                                                         #
#---------------------------------------------------------#
FROM postgres:11
ADD scripts/*.sql /docker-entrypoint-initdb.d/
