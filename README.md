# Food buddy

Food buddy app is made for everyone who'd like to write, share and explore different recipes. These recipes include breakfast, lunch, dinner, desert, snacks as well as coctails.
The app was created using clojure language. After registering, each user has the ability to write and share recipes, as well as see all the recipes within database. Additionally, each user can add a new ingredient in order to write the recipe properly. 
Application also suggests random coctails to a user. This functionality is obtained from [The coctail DB][6] 

## Environments and libraries

The application was generated using [Leiningen][1], [Ring][2], [Compojure][3], [Migratus][4],
[Buddy-auth][5], [Korma][7] and the [the coctail DB][6] API. The short explanation of each of these follows:

[Leiningen][1] is build automation and dependency management tool for Clojure.

[Ring][2] is a Clojure web applications library. By abstracting the details of HTTP into a simple, unified API, Ring allows web applications to be constructed of modular components that can be shared among a variety of applications, web servers, and web frameworks.

[Compojure][3] is a small routing library for Ring that allows web applications to be composed of small, independent parts.

[Migratus][4] is a Leiningen plugin general migration framework, with an implementation for database migrations, for the 
Migratus library. Migratus library is a general migration framework, with implementations for migrations as SQL scripts or general Clojure code.

[Buddy-auth][5] is a module of Buddy secuirty library that provides authentication and authorization facilites for ring and ring based web applications.

[Korma][7] is one of the most popular SQL DSL for Clojure. It uses a macro-based DSL to represent queries as if they were Clojure forms.

[the coctail DB][6] - An open, crowd-sourced database of drinks and cocktails from around the world.

The application was generated using IntelliJ IDEA and SQLYog.

[1]: https://github.com/technomancy/leiningen
[2]: https://github.com/ring-clojure/ring 
[3]: https://github.com/weavejester/compojure
[4]: https://github.com/yogthos/migratus
[5]: https://github.com/funcool/buddy-auth
[6]: https://www.thecocktaildb.com
[7]: https://github.com/korma/Korma

## Running the project

You will need [Leiningen][1] 2.0.0 or above and MySQL installed.

Follow the next steps:

1. Login to the MySQL server and create database clojure with the following command:

	CREATE DATABASE clojure DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

2. Navigate to your project directory and execute the following command in terminal:

    lein migratus migrate

3. To start a web server for the application, run:

	lein ring server
	
4. Create your user on the registration page and then login. **When creating the user password should contain at least 6 characters.**

## About project
The project consists of several pages:
1. **Login page** - the first page that appears when running the project.
2. **Register page** - page where new users should register. When creating a user password should be longer than 6 characters.
3. **Logout** - logs user out when clicked.
4. **Recipe page** - is a home page which gives the overview of all recipes in database. Names and types of recipes are presented. Moreover, user is able to search recipes by name.
5. **View recipe** - a page that's generated when user clicks on any of the recipes on home page. This page includes all details of a recipe - its name, description as well as list of ingredients along with measures.
6. **Add ingredient** - page where user can see all the ingredients existing in database. User can also search through the ingredients as well as add new ingredient.
7. **New recipe** - page for creating the recipe. User enters name, description and ingredients for the recipe. When entering ingredients user can search through all the ingredients existing
in the database. Additionally, user can enter up to 10 ingredients for the recipe while new fields for ingredients are generated dynamically when user clicks on button. After creating the
recipe it is stored within database and user is redirected to the home page (recipe page).
7. **Coctails** - a page where random coctails are presented. This is obtained through the use of [the coctail DB][6] API. The random coctail is presented through its photo, ingredients with measures and instructions for preparing it.

###### The project was developed as part of the assignment for the course Software Engineering Tools and Methodology at the Faculty of Organization Sciences, University of Belgrade, Serbia.
