Prerequisites:

  + Apache 2.x
  + Tomcat 6.x or other java servlet server (JBoss)
  + MySQL 5.x
  + Spring Instrument Tomcat Weaver (spring-instrument-tomcat-3.x.x.jar)
      http://ebr.springsource.com/repository/app/bundle/detail?name=org.springframework.instrument.tomcat
      
      The jar file for this should be placed into your servlet's "lib" directory
      
  + Unzip apache-tomcat-6.x.x.zip into the projects root, and adjust the 'tomcat' symlink to point
    at apache-tomcat-6.x.x's directory; as an example, if you were using tomcat 6.0.35:
    
    ln -s apache-tomcat-6.0.35 tomcat
      
  + Your servlet server will need to have following JAVA_OPTS set:
  
      -Depf.props=/etc/portfolio/epf.properties -Depf.log.level=DEBUG
      
      Set epf.props to the location of a configuration file; this is used to override default values
       and is required

      Set epf.log.level to INFO for less verbose output 
      
      The maximum memory (-Xmx) option should be set to a value greater than 512MB (e.g. -Xmx1024m)
      
  + In your my.cnf file (in /etc/mysql), the following needs to be set in the [mysqld] section:
  
      lower_case_table_names=1
     
  + The default login method is via username and password stored in the "person" table
  
      username:  root@localhost
      password:  password
 
  + The MySQL credentials and other settings are located in:

      src/portfolio/etc/portfolio.properties
 
      To set your MySQL parameters (found in portfolio.properties):

        portfolio.jdbc.user=portfolio
        portfolio.jdbc.password=password

    
Instructions:

  + Load the schema (and its associated data) into a MySQL database
     
     The default configuration/schema assumes a database named 'portfolio'
     
     The schema is located in the directory 'src/portfolio/sql'
     
     Load:
     
       cat src/portfolio/sql/portfolio5.sql | mysql -u root -p portfolio
     
     (the sql script, by default, will drop the database, portfolio, if it
      exists)
     
  + Build the project's war file:
  
    ./package.sh
    
    If the build is successful, the subsequent war file will be located at:
    
    src/portfolio/build/deploy/ROOT.war
    
  + Deploy the war file by placing it into the servlet server's application
    directory; (e.g. Tomcat6 on Debian, place in /var/lib/tomcat6/webapps)
    
    
    
