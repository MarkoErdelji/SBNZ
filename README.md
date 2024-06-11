# Uputstvo za pokretanje projekta

## Baza podataka

Potrebno je instalirati docker i uraditi sledece komande:
```bash
docker pull postgres
docker run --name my-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres
```

## Backend

Potrebno je iskoristiti Intellij za paljenje spring boot aplikacije,entry point je ServiceApplication.


## Frontend

Potrebno je ući u folder front

```bash
cd front
```

zatim uraditi sledece komande

```bash
npm install
npm start
```

Frontend react aplikacija je onda pokrenuta na portu 3000.
