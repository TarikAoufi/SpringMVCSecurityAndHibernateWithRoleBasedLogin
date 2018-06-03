package fr.aoufi.springmvcsecurity.runtime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Ce démo montre l'utilisation de la classe BCryptPasswordEncoder, définie en tant que bean 
 * dans notre configuration pour la sécurité de l'application, qui implémente l'interface PasswordEncoder et 
 * fournit un hachage de mot de passe. 
 * 
 * Le hachage de mot de passe est l'une des principales considérations lors de la conception d'une application sécurisée.
 * Il est basé sur l'algorithme BCrypt qui génère une chaîne aléatoire de longueur 60, pour 
 * chaque appel aura un résultat différent, nous devons donc nous assurer que le mot de passe sera stocké 
 * dans une colonne qui peut l'accueillir.  
 * Une erreur courante consiste à créer une colonne d'une longueur différente, puis à obtenir une erreur 
 * de nom d'utilisateur ou de mot de passe invalide au moment de l'authentification.
 * 
 */

public class PasswordEncoding {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("abc1"));
	}
}