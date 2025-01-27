make run:
	mvn -Pct clean package -DskipTests=true docker:build docker:run

make build:
	cd dv-marketplace && npm run build && cd -
	mvn -Pct clean package -DskipTests=true docker:build docker:build