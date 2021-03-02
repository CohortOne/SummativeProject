carDate.zip
2021-02-12:
Hi all, the previous version was not tested and have errors.  Please use this version:

1: Added common.html, which contains declaration of common header, navigation bar, and footer to be included in selected pages.
2. Added home.html as the landing page for all users.
3. Revised table ROLE so as to make use of spring security5 tags on html pages to selectively show shortcuts base on user roles.
   - all roles must be renamed to ROLE_xxxxx for the framework to work.
4. Added thymeleaf navigation bar to home and Employee pages, show and hide base on user ROLES.
5. Revised sql_scipts.
6. Coded Customer and Vehicle POJO
7. Coded CustomerController and Customers.html.

Encountered one problem:
Customer.class uses custId as @Id, and it store customer contact details.
To capture an alternate contact for each customer, the attribute "private Customer custLinked;" is added to "public class Customer", 
with these code:
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "CUSTIDLINK", nullable = true)
    private Customer custLinked;
which resulted in the column CUSTIDLINK created in Oracle table CUSTOMER.

Let's say custA has custB as custLinked, and custB have custC as custLinked, then when custC is to be updated with custA as custLinked,
the app will crash with stack overflow error.  It seesm to me that as the update creates a ring strcuture, when Spring Framework loads custA,
it continues to load custB as custLinked, while custB loads custC as custLinked, and custC loads custA as custLinked, and this went into
a endless loop.

I checked the FetchType, you only have EAGER and LAZY.  Even after I changed it to LAZY, the same problem presists.

I have brought this up with Simon.

In the meantime, may proceed to code Vehicle.html and VehicleController.java similar to Customer.

This problem was eventually traced to the toString() methods of the pojo.

Customer.toString() apart from printing class attributes, also print the Customer referred to by CustLinked.  CustLinked is a Customer itself, so
on printing it will also try to prints its CustLinked, and thus resulted into an endless loop.  Besides, such endless loop, another problem
likely to occur is attemting to print CustLinked.custName where CustLinked itself is null.

Problem resolve, 2021-02-14.


 
carDate 02-13.zip
2021-02-13:
1. Vehicle.java: Added attribute dailyRate with getter and setter, generated toString().
2. VehStatus.java: Added getters and setters, generated constructor and toString().
3. Vehicle: added VehStatus Repository and VehicleController.
4. Vehicles.html: added web page for Vehicles maintenance.
5. Customers.html: added ability to display pinned vehicle.


carDate 02-14.zip
2021-02-14:
1. Customer.java: 
   = added methods to add and remove Hire.
   = toString to not print custLinked (which is the cause of stack overflow error),
   = change FetchType back to EAGER.
2. Hire.java: Corected some mistakes, enriched into a POJO.
3. Added HireDao, HireRepo, HireDaoImpl, and HireController, and Hires.html to maintain Hires.
4. Vehicle.java: added OnToMany relation to track hire history.
5. Customers.html: revisions to fix various problems.
6. Vehicles.html: revisions to fix various problems.
7. sql_script to adapt to database changes.

Application is now able to maintain Employees, Customers, Vehicles, and Hires.
More enhancement needed on Navigation, and fee computation automation.


carDate 02-15.zip
2021-02-15:
1. Hire.java: added DailyRate for hire fee computation.  This is because Vehicle.dailyRate may be updated while a Vehicle is on hire. The old rate should be used for that hire.
2. HireController: added function to Hire and Return vehicles, and function to compute hire fee.
3. VehicleController.java: Catch database save/update exception to display error message to user.
4. CustomerController.java: Catch database save/update exception to display error message to user.
5. BusinessConfig.java: added effective date and extra-time surcharge.  This class is not in use yet.
6. 4 .html, general enhancement.
7. sql_script to adapt to database changes.

carDate 02-17.zip
2021-02-17:
1. Controllers and htmls: Employees/Customers/Vehicles/Hires fix a page navigation error common to all pages.
2. Customer.html: move some buttons around, Added tool-tips for Alt-contact buttons.
3. Customer.html, CustomerController: correct an error in the url for Alt-contact maintenance buttons.
4. sql_scripts: added script to genearate some test data for customers.
5. HireController: Correct method to delete Hire.

carDate 02-18.zip
2021-02-18:
1. common.html, and all Controllers: added session attributes currFunc and keyword, selective display of sessions attributes relevant to currFunc.
2. Customers.html: converted most buttons into fa icons [https://fontawesome.com/v4.7.0/icons/].
3. Customers.html, CustomerController:
   - Added input and apply/clear keyword to filter displayed customers.
4. CustomerRepo, CustomerDao, CustomerDaoImpl, CustomerController: added codes to support filter.


carDate 02-19.zip
2021-02-19:
1. Added package carDate.pict for handling of pictures.  A new pojo is created to store pictures in the database.
2. Customer.java: created an optional one-to-one link to Picture.java to store a picture of the custoer, understandably the driving license.
3. application.properties: to define max upload file size
3. Customer.html, CustomerController: modified to accomodate the upload, download, view, clear, of Customer Picture. 
   - ability to handle max upload file size exceeded error.
     - however, when such error occurred, the current customer record and the image file on screen will be lost, 
	   and the server will temporaroily lost the ability to format session creation and last access date time properly on web page.
https://bezkoder.com/spring-boot-upload-file-database/
https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
https://www.websparrow.org/spring/spring-boot-display-image-from-database-and-classpath



carDate 02-20.zip
2021-02-20:
1. Customer.html:
   - replacement of bootstrap.js, bootstrap.css, jquery.js
     as per https://getbootstrap.com/docs/5.0/components/modal/
   - remove charts/loader/js as it is not required.
   - use of onclick="return confirm('confirmation message')" on update buttons in place of tool-tips.
   - introduction of modal to display picture of customer driving license.
     - on the Customer list screen, customer picture if exists is displayed as an tiny thumbnail.
	 - on clicking the thumbnail, the picture is displayed on a modal.
	 - on the modal is a detele button to delete the picture
	 - clicking close button or anywhere outside of the modal closes the modal.
2. CustomerController.java:
   - Method/mapping custPict/{pictId} changed to retrieve specified customer picture for presentation on modal.
   - minor fixes.
3. CustomerDaoImpl.java:
   - minor fix.
4. Note, this version has a technical problem.  A temporaroily fix has been used to neutralise it.  
   Need further investigation to identify the root cause and rectify it.
   Details can be found in "mysterious double call of custSort.docx"
   
These web pages were also consulted in the development of picture handling:
https://www.pixeltrice.com/image-gallery-spring-boot-application-using-mysql-and-thymeleaf/
https://mdbootstrap.com/docs/standard/components/modal/



carDate 02-22.zip
2021-02-22:
1. Latest implemetation of the handling of Customer pictures of driving license on modal:
   - On the Customer list screen, customer picture if exists is displayed as an tiny thumbnail.
     If there is no picture, an icon is displayed instead.
   - On clicking the thumbnail/icon, a modal appears, and the current picture is displayed if any.
   - If there is a current picture, a detele button will show that can be used to delete the picture.
   - Clicking close button or anywhere outside of the modal closes the modal.
   - A new picture can be uploaded from the modal window.  New picture replaces old picture if any.
2. Removed picture upload feature from Customer list screen.
3. Fixed the "mysterious double call of custSort" problem, by merely changing th:src="xxx" into src=""
   in the modal in n Customers.html.
4. Customer.html, Vehicles.html: use of class="" to color tables.   
   



carDate 03-01.zip
2021-03-01:
1. CustomerController.java: 
   - Added method to un-pin Vehicle. This function was overlooked earlier.
2. Hires.html:
   - added button to invoke generation of invoice in PDF format.
3. HiresController.java:
   - added method to generate invoice in PDF format.
   - enhanced method computeFee to add billing details to the PDF invoice.
4. pom.xml:
   - added dependency for PDF generation.
   


carDate 03-02.zip
2021-03-02:
1. Customers.html:
   - assign html element id for each pinCust and listCust picture containers so that they can be modified by JS.
   - swap customer and alt-contact linking order.
   - revisions to modal to facilitate functional enhancement.
   - move <script> to Customers.js
2. Customers.js:
   - Takes over scripts from Customers.html that causes modal to show.
   - added new ajax scripts to enabled modal to be sticky.
   - upon upload and delete of pictures, able to modify necessary html elements to reflect the changes without reloading the entire page.
3. UploadForm.java
   - create to facilitate the upload of pictures using the modal.
   - the design is inspired by the web resources: //https://o7planning.org/11813/spring-boot-file-upload-with-jquery-ajax
4. CustomerController.java: 
   - @GetMapping("/custLink/{theCustId}"): modified to link theCustId as the alt contact of pinCust,
     instead of the reverse as before.
   - @GetMapping("/custRmvPict/{custId}") changed to @GetMapping("/custRmvPictJs/{custId}")
     and modified so that the picture deletion is triggered by ajax calls without re-drawing the entire html page.
   - @PostMapping("/custSavePict") changed to @PostMapping("/custSavePictJs")
     and modified so that the picture upload is triggered by ajax calls without re-drawing the entire html page.
  



Application is able to fully function.
Further enhancement needed:
1. To extend the picture handling feature to Vehicle module.
	- Vehicles can have multiple picture.  The modal has to have <prev> and <next> buttons to navigate.
2. Filter implemented for Customer.  To further apply the same for Employees, Vehicles, and Hires.
3. Consider making use of the search dialog on the navigation bar in place of filter.
4. Large text labeled buttons in Customer.html replaced with small icon buttons.  Continue to do the same for the other four htmls.
5. Continue to use onclick="return confirm('xxx')" as tool-tips for small icons.  Continue to beautify buttons.
6. Allow employee to change own password.  
7. Introduce a new initial menu field for Employee POJO.
   - user can change it to Administration(default), Customer, Vehicle, or Hire.
   - user cannot change to a menu he/she is not given the role to access.
   - upon sign-in, the first screen will depend on this setting.
   - if initial menu is not amongst user roles, dislay administration (show user personal details) instead.
   - So all together, users can change own password and own initial menu.
8. Implement BusinessConfig.java, which has to be stored in the database.
   - Multiple BusinessConfig record can exist in database, each with a different effective date.
   - Allow Manager/Admin to maintain BusinessConfig.
   - BusinessConfig of past date cannot be changed.
   - only BusinessConfig of effective date today or after can be added.
   - only BusinessConfig of effective date in the futurcan be modified.
   - modify hire fee calculation and other relevant functions to use this table.
9. Generate charts for Cars utilization. 
   a. Cars not generating income for last <x> days, 
   b. Cars with utilization below <y>% for the last <z> days.
   c. Average daily utilization (hours rented / 24) of the last <n> days, by Car and overall.
   d. Income generated in the last <n> days, by Car and overall.
   e. Percentage of early, on-time, late return of Cars in the last <n> days.
10.Test mode clock.
   - All reference to "java.time.LocalDate.now()" to be centralised into one method.
   - the method will look for a TimeWarp.
   - TimeWarp must be 0 in production mode, but can be adjusted in test mode.
   - the method will reutrn java.time.LocalDate.now() + TimeWarp to the caller who will use it as if it is the clock.
   - TimeWarp can be introduced as BusinessConfig attribute, and be maintained only by Admin.
   - When maintaining, TimeWarp can only be increased, not decreased.
   - Upon sign-in, check this value, and alert the user that he/she is using a testing system if TimeWarp is not 0.
11.Design more fluid navigation.
12.Handle concurrent update.


