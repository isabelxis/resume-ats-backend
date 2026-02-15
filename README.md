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

- CRUD Currículo
- CRUD Experiência ✔
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

- CRUD Educação
- CRUD Projeto
- CRUD Skill

3. Seleção de template ATS

4. Validação ATS

5. Download do currículo

