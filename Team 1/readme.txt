Project utilized these Spring Boot features:

1. security, so that users are created, with encrypted password, and multi-roles, which are validated in the application.
2. Use of request attributes (Model model.get/setAttributes)
3. Use of session attributes (Session session.get/setAttributes)
4. Use of session attributes to simplify the .html pages while maintaining session persistenance.



2021-01-29 add @EnableJdbcHttpSession
change from Copy (4) to use session attributes instead of model attributes to simplify url coding in html.


2021-02-02 Enable User -> USers_Roles <- Role
Allow maintenance of multiple user roles, and Bcrypt password hashig.
Breakdown into multiple packages.
Change oneBank.Customer package to also use more session attributes.

2021-02-03
Trying...
Add Oauth2 (authentication by social app)

			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
			
Added statistical data graphics.

2021-02-04
Added Accounts object and page.  Successfully displayed/captured Customer name and account type in Account maintenance screen 
using ONE-to-MANY relationship.

2021-02-05
Rectified a problem with the return and saving of Account.AcctType.
Added Swagger.

Added unique=true annotation to Customer.Nric to check for alternative unique key.


2021-02-08
Try to add Ajax feature to prompt for user confirmation before a update takes place.