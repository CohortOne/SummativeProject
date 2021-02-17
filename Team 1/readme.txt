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

2021-02-17:
1. Controllers and htmls: Employees/Customers/Vehicles/Hires fix a page navigation error common to all pages.
2. Customer.html: move some buttons around, Added tool-tips for Alt-contact buttons.
3. Customer.html, CustomerController: correct an error in the url for Alt-contact maintenance buttons.
4. sql_scripts: added script to genearate some test data for customers.
5. HireController: Correct method to delete Hire.


Application is now able to fully function.
Further enhancement needed:
1. Filters for all web pages to help locate customers or pick vehicles.
2. Replace large text labeled buttons with small icon buttons with tool-tips.  Streamline buttons.
3. Design more fluid navigation.
4. Upload, store, display, delete Customer driving license and Vehicle pictures.
5. Generation of invoice.



https://bezkoder.com/spring-boot-upload-file-database/
