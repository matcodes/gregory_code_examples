CODE EXAMPLES:
--------------

NOTE: Maven and Java 6 is required to be installed to run all these.

1. Embedded Database
--------------------

The directory for this is embedded_db_and_mvn_example.

Change to this directory and run (Maven is required to be installed in your setup):

mvn test -q

Highlights:

1. The above code is using Maven for build.
2. The main test code is

./embedded_db_and_mvn_example/src/test/java/com/javademo/test/derby/dao/PersonDaoTest.java

3. It loads data from Excel file and inserts into the embedded Apache Derby DB.

./embedded_db_and_mvn_example/src/test/resources/com/javademo/test/derby/dao/person.xls

4. It uses JUnit for demonstrating the example.
5. It simply reads one record using primary key "1" (Refer to the Excel)


2. Decentralized Security Approaches - PKI
------------------------------------------

The directory for this is pki_crypto.

The source here is extracted from actual code that was written for real projects.

1. PKIUtil.java: Number of PKI Methods for signature verification / encryption.


2. CryptoUtil.java: Symmetric encryption examples (uses AES encryption)

2a. Change to the above directory.
2b. Run java CryptoUtil
2c. The code does a AES encryption test.

3. Password Hashing
--------------------

The directory for this is password_hashing.

The source here is extracted from actual code that was written for real projects.

1. ShaPasswordEncoder.java: SHA digesting passwords with salt

1a. Change to the above directory.
1b. Run java ShaPasswordEncoder

A sample hashing test is done when you do the above.

4. Sample Accessing Replicated databases
----------------------------------------

The directory for this is mysql_master_slave.

This is a typical MySQL sample code to demo:

1. Setup of connections
2. Differentiate between update and select

A much more detailed example would take a longer time to write. Due to NDA restrictions
we are unable to show more real world code here.

5. Using open source java libraries
-----------------------------------

The directory for this is embedded_db_and_mvn_example (Same as 1).

One good way to integrate open source libraries to your project is to make the project a Maven one.

In this example, we have used Derby as as well as DBUnit libraries are integrated by 
simply declaring their dependency in the pom.xml like below:

		<dependency>
		
			<groupId>org.dbunit</groupId>
		
				<artifactId>dbunit</artifactId>
	
						<version>2.2.2</version>
			<scope>test</scope>
		</dependency>

					<dependency>
			<groupId>org.apache.derby</groupId>
			<artifactId>derby</artifactId>
			<version>10.9.1.0</version>
		</dependency>




