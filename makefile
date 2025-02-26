make install:
	cd dv-marketplace && npm install && cd -

make react-run:
	cd dv-marketplace && npm run dev && cd -

make boot:
	mvn spring-boot:run

make boot-b:
	cd dv-marketplace && npm run build && cd -
	mvn spring-boot:run

make run:
	mvn -Pct clean package -DskipTests=true docker:build docker:run

make build:
	cd dv-marketplace && npm run build && cd -
	mvn -Pct clean package -DskipTests=true docker:build docker:build
make schemaspy:
	# https://github.com/schemaspy/schemaspy/releases
	# https://jdbc.postgresql.org/download/
	java -jar ~/Downloads/schemaspy-6.2.4.jar -t pgsql -host localhost -db dv_mkt_pg -u admin -p admin -s public -dp ~/Downloads/postgresql-42.7.5.jar -o /tmp/schemaspy
