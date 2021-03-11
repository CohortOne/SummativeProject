Version as at March 8th deployed to Heroku.com.  Access by this URL:


https://cardate.herokuapp.com/login



The following is the development history:



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


carDate 03-04.zip
2021-03-04:
This is a minor change, as I position to roll out picture handing for Vehicle module.
1. Customer.java
   - corrected the relation between customer and current hire from many-to-1 into 1-to-1.
2. HomeControllers.java
   - removed unnecessary codes.
3. CustomerControllers.java, EmployeeControllers.java, HireControllers.java, VehicleControllers.java
   - Remove all hasRole method in the controllers, and changed all to refer to the one copy in HomeController.
4. RestGlobalExceptionHandler.java, CustomerControllers.java
   - Created RestGlobalExceptionHandler.java to trap upload file size exception error.  Mechanism not understood, but it works.
   - The previous filesize exception method becomes obselete and is removed from CustomerControllers.java.
5. PictureController.java, Customers.html, CustomerController.java, Customers.js
   - PictureController.java is created to handle Pictures as it is used by both Customer and Vehicle modules, 
     and may be later on by Employee too.
   - PictureController.java has method and entry point pictGet{pictId} to serve picture.
     It take over the method/entry point custGetPict{pictId}.
   - Customers.html and Customers.js are modified to refer to the new entry point for pictures.
   - CustomerController.java is striped of the obselete entry point custGetPict{pictId}.
6. Vehicle.java, Vehicles.html. sql_script
   - Corrected the relation between Vehicle and current hire from many-to-1 into 1-to-1.
   - Added picture and pictures attribute.
   - Added EnggCap, bhp, and topSpeed attributes
   - Attribute picture is the profile picture of the Vehicle.
   - Attribute pictures is a set of 0 to many pictures of the Vehicle.
   - picture if present, is one of the Pictures in pictures.
   - Vehicles.html has columns added for picture, engCap, bhp, abnd topSpeed.
   - This is in preparation for the roll out of pictures capability for Vehicle.
   - sql_scripts modified to drop one more table, and to populate Vehicle table.
7. Customers.html:
   - Display of pinVeh is now on a standalone table, instead of embedded in one row of the Customer table.
   - Added title= as tool-tips, and onclick=return confirm() to customer activation/deactivation for pinCust as well as listCusts.
   - enable pinVeh from pinCust and listCusts if the customer has a current Hire.
   - Added title= as tool-tips to customer edit/delete/clone for pinCust as well as listCusts.
   - page navigation button change back to text buttons for Prev and Next.   
7. Employees.html:
   - Move Employee Activation/Deactivation from Actions button to the IsActive cell.
   - Added title= as tool-tips, and onclick=return confirm() to Employee activation/deactivation.
   - Added title= as tool-tips to Employee edit/delete/clone.
   - page navigation button change back to text buttons for Prev and Next.
   - Beautify some buttons
8. Home.java
   - Removed obselete codes.
9. Customers.js:
   - include script to reset the execution result message upon modal.show.
   - use java script in-place of jquery whenever possible.
More car pictures and details at
https://europeluxurycars.com/rent-luxury-cars-france/nice?camp=ppc_en&gclid=CjwKCAiAp4KCBhB6EiwAxRxbpNP4cbgiOGExWYRYVpAmPg8xQc7by84gWYd7Hutr4NLJlP7lAIasPhoCJ1kQAvD_BwE
   
carDate 03-05.zip
2021-03-05:
Objectives of this upgrade:
A. Allow pictures to be stored in file system, as an additional option to storing in database.  Now, Customer pictures are stored in DB, and 
   Vehicle pictures in file system.  Both will have database records, only the binary data of Vehicles are stored in file system using the 
   pictId as file name.
   Pictures or any large data, are preferable stored in file servers instead of databases due to the lower unit cost of storage.
   Vehicle pictures are stored in one single folder.  Each file is named by pictId.
B. To prepare for handing multiple pictures per Vehicle, one more field is added to the picture UploadForm.
   Currently, the id field is used to hold custId.  In the new version, id field will hold pictId, and new entityId field is for custId or vehId.
   When handling Vehicle pictures, it will be necessary to also carry the PictId since Vehicles have multiple pictures.
Changes:
1. Allow pictures to be stored either in database, or in file system.
   PictureStorageService.java:
   - able to store and delete pictures in file system as well as database.
   PictureController.java:
   - able to serve pictures regardless of storage medium of file or database.
   CustomerController.java, 
   - touch up for new picture handling
2. Added entityId field to picture file upload form, so that PictId is also POSTed.
   It also paves the way for handling multiple pictures for each Vehicle in the next version.
   UploadForm.java
   - add a new input field to hold the entityId, which is CustId if picture being uploaded belong to a Customer, or vehId for Vehicle.
   - id field is reserved for pictId. The use of pictId allows a picture to be replaced, instead of always delete and create new one
   Customers.js, Customers.html
   - enhanced to use the new entityId field, and program logic correction and optimization
   Vehicles.js, Vehicles.html
   - replicated picture handling feature from Customer module.
   VehicleController.java
   - added entry points to save and remove Vehicle Pictures.
   - modified method vehDeleOts to perform referential validations before allowing deletion of Vehicle record.
3. EmployeeController.java, Employees.html
   - minor touch-up: shorten Delete and Update to Dele and Upda in method names and entry point
4. Provided driving license and car pictures in folders for your convenience.   
  

  
CarDate 03-08 Heroku.zip
Heroku version. Deployed to Heroku.com
1. PictureStorageService.java
   - replace isBlank() with isEmpty().  isBlank() not allowed in Heroku.com.
2. application.properties
   - repalced some lines for Heroku PostGreSQL
3. data.sql
   - these are for initiation of data in PiostGreSQL database if deployed to Heroku.  They are same as sql_script, but with minor changes
     adapted to PostGreSQL.


