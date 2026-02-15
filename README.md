# Backend resume-ats


## Fluxo do Usuário

1. Cadastro/Login
 - Registrar ✔
  - POST http://localhost:8080/api/auth/register
    ````
    {
      "email": "teste@gmail.com",
      "password": "12345678"
    }
    ````
 - Login ✔
  - POST http://localhost:8080/api/auth/login
    ````
    {
      "email": "teste@gmail.com",
      "password": "12345678"
    }
    ````
 - Envio de e-mail "Esqueceu Senha" ✔
  - POST http://localhost:8080/api/auth/forgot-password
    ````
    {
      "email": "teste@gmail.com"
    }
    ````
 - Resetar senha ✔
  - POST http://localhost:8080/api/auth/reset-password
    ````
    {
      "password": "12345678"
    }
    ````
 - Alteração de profile ✔
  - PUT http://localhost:8080/api/users/me
    ````
    {
      "email": "teste@gmail.com",
      "github": null,
      "linkedin": null,
      "name": "Jane Doe",
      "phone": "+55(85)9999-9999",
      "portfolio": null
    }
    ````

2. Preenchimento do currículo (wizard)

- CRUD Currículo ✔
POST http://localhost:8080/api/resumes
````
{
  "title": "Fullstack Developer",
	"summary": "Backend and Frontend developer with 5+ years experience in Java, Spring Boot and React"
}
````
GET http://localhost:8080/api/resumes
GET http://localhost:8080/api/resumes/{id}

PUT http://localhost:8080/api/resumes/{id}
````
{
  "title": "Fullstack Developer",
	"summary": "Backend and Frontend developer with 5+ years experience in Java, Spring Boot and React"
}
````
DELETE http://localhost:8080/api/resumes/{id}

- CRUD Experiência ✔

POST http://localhost:8080/api/resumes/{id}/experiences
````
{
  "company": "Teste Company",
  "position": "Fullstack Developer",
  "description": "Desenvolvimento usando Angular e Spring Boot",
  "startDate": "2021-01",
  "endDate": "",
	"skills": null,
	"models": "ON SITE",
  "current": true
}
````
GET http://localhost:8080/api/resumes/{id}/experiences
GET http://localhost:8080/api/resumes/{id}/experiences/{id_experience}
PUT http://localhost:8080/api/resumes/{id}/experiences/{id_experience}
````
{
  "company": "Teste Company",
  "position": "Fullstack Developer",
  "description": "Desenvolvimento usando Angular e Spring Boot",
  "startDate": "2021-01",
  "endDate": "",
	"skills": null,
	"models": "ON SITE",
  "current": true
}
````
DELETE http://localhost:8080/api/resumes/{id}/experiences/{id_experience}

- CRUD Educação
- CRUD Projeto
- CRUD Skill

3. Seleção de template ATS

4. Validação ATS

5. Download do currículo

