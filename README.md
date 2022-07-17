
# NickChanger

Ce plugin à été crée pour le serveur Metagord. Il permet de faire /nick afin de se cacher des autres joueurs.






## Installation

Afin de faire fonctionner le plugin, il suffit de mettre le `NickChanger-1.0-SNAPSHOT.jar` dans le dossier plugins de votre serveur. Pour télécharger le plugin, merci de vous rendre [ici](https://github.com/KendaFR/NickChanger/releases/).


## Configuration

`config.yml`:
 Ce fichier de configuration va permettre de modifier les messages en jeu, ainsi que modifier la permission de la commande `/nick`

 `grades.yml`: Ce fichier va permettre de géré également des grades customisé. Vous pouvez en rajouter autant que vous voulez avec ce pattern:

```
grades:
    admin:
        displayName: '&cAdmin'           //Le nom afficher en jeu du grade
        power: 50                        //La puissance du grade (va permettre de géré l'affichage ou non de grade supérieur)
        permission: nick.admin           //La permission que dois avoir l'utilisateur pour afficher ce grade 
 ```
## Commande

Il existe deux commandes
```
  /nick <pseudo> (compris entre 3 et 16 caractères)
  /nick (si le joueur à déjà un nick)
```

## Contact

 - [Mes autres projets](https://github.com/KendaFR)
 - [Youtube](https://www.youtube.com/channel/UCSQqWKgspiE3JvHi5_vFLsw)

