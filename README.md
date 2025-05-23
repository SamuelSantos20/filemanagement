 <h2> Projeto Gerenciamento de Arquivo</h2>
 <h3>A ideia para a criação deste projeto surgiu do desejo de desenvolver algo que despertasse nossa curiosidade, que nos fizesse pensar mais, melhorar nossa capacidade de resolver problemas e, claro, criar algo diferente.
  Este projeto surgiu com o objetivo de despertar ainda mais a nossa curiosidade pela linguagem Java e pelas ferramentas do ecossistema Spring. Através dele, 
   buscamos não apenas consolidar nosso aprendizado, mas também criar algo útil que possa ajudar outras pessoas. 
   Cada funcionalidade desenvolvida representa um passo a mais na nossa jornada de crescimento como programadores.</h3>


 ## 🚀 Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **PostgreSQL**
- **BlackBlaze B2 (armazenamento em nuvem)**
- **Maven**
  ## 📋 Funcionalidades

- [ ] Cadastro de usuários
- [ ] Autenticação com Spring Security
- [ ] Integração com banco de dados PostgreSQL
- [ ] Upload e download de arquivos com BlackBlaze
- [ ] Agendamento de tarefas (se aplicável)

### Pré-requisitos

- Java 23+
- PostgreSQL instalado e configurado
- Conta e bucket no BlackBlaze B2
- IDE de sua preferência (IntelliJ, VS Code, etc.)

1. Clone o repositório:
   ```bash
   https://github.com/SamuelSantos20/filemanagement.git
   ```
2. Configuração Postgres(application.properties ou application.yml)

```bash
   spring.application.name=Api
   spring.jpa.database=POSTGRESQL
   spring.datasource.platform=postgres
   spring.jpa.show-sql=true
   spring.datasource.driver-class-name=org.postgresql.Driver
```
3. Configuração BlackBlaze
   ```bash
    amazon.aws.credentials.access-key=${B2_ACCESS_KEY_ID}
    amazon.aws.credentials.secret-key=${B2_SECRET_KEY}
    amazon.aws.credentials.bucket-name=${B2_BUCKET_NAME}
    amazon.aws.credentials.endpoint= https://s3.us-east-005.backblazeb2.com
    amazon.aws.credentials.region=us-east-005
   ```

   ✅ Contribuidores
   
   . Wallace Prudencio Delphino<p>
   . Samuel dos Santos

  
