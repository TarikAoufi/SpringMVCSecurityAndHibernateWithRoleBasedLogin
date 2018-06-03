# SpringMVCSecurityAndHibernateWithRoleBasedLogin

Les outils et technologies utilisés pour cette application sont:

•	Spring MVC 5.0.6.RELEASE

•	Spring ORM 5.0.6.RELEASE

•	Spring Security 5.0.5.RELEASE

•	Hibernate Core 5.3.1.Final

•	Hibernate Validator 5.4.2.Final

•	C3p0 0.9.5.2

•	Servlet API 4.0.1

•	JSP API 2.3.2-b02

•	JSTL 1.2

•	MySQL Connector 8.0.11

•	Common DBCP2 2.2.0

•	JAX-B API 2.2.11

•	JDK 1.9

•	Bootstrap v4.0.0

•	Maven 3.7.0

•	Jetty Maven plugin 9.4.8

•	Eclipse Oxygen.3a Release (4.7.3a)

•	MySQL Server 5.7





Dans cette application web on gère l’inscription de nouveaux utilisateurs, l’authentification en vue d’identifier ses utilisateurs et de sécuriser l’accès à d’éventuelles données. Elle est basée sur Spring MVC 5 et complètement sécurisée à l’aide de Spring security 5, intégrant la base de données MySQL avec Hibernate 5, stockant les mots de passe au format crypté en utilisant Bcrypt, fournissant la fonctionnalité RememberMe en utilisant une implémentation personnalisée de l’interface PersistentTokenRepository avec Hibernate, incluant la fonctionnalité d'internationalisation (i18n) de Spring, et permettant de changer le thème de l’application à l’aide du résolveur de thèmes du framework Spring.

Dans cette application, l'utilisateur peut être associé à un ou plusieurs rôles, montrant une relation plusieurs-à-plusieurs. L'utilisateur disposant du rôle Admin peut créer, éditer, supprimer un utilisateur existant ou modifier son rôle, et lister tous les utilisateurs (opérations CRUD). La modification d'un compte utilsateur est possible aussi avec un utilisateur ayant le rôle DBA. Seule l'opération qui permet de lister les utilisateurs est commune à tous les rôles (Admin, DBA, USER).

Les URL de l'application sont sécurisées à l'aide de Spring Security. Cela signifie que, selon les rôles de l'utilisateur connecté, l'accès à certaines URL sera accordé ou interdit. Sur la couche de vue, l'utilisateur ne verra que le contenu qu'il est autorisé à utiliser en fonction des rôles qui lui sont attribués, grâce aux balises Spring Security pour la couche de vue.
