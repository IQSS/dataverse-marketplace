make install:
	cd dv-marketplace && npm install && cd -

make react-run:
	cd dv-marketplace && npm run dev && cd -

make run:
	mvn -Pct clean package -DskipTests=true docker:build docker:run

make build:
	cd dv-marketplace && npm run build && cd -
	mvn -Pct clean package -DskipTests=true docker:build docker:build