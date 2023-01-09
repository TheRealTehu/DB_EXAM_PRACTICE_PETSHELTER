# **DB GYAKORLÓ FELADAT**  
Egy állatmenhelynek segítesz rendszerezni, hogy melyik állat mikor és kinél volt ideiglenes örökbefogadásban. A megfelelő adatokat adatbázisban tárolják. Neked ezek alapján kell bizonyos lekérdezéseket elvégezned. A feladatod részletei a következők:  
## **Adatbázis**  
Az adatbázisban 2 tábla van `owners` és `pets` néven.  
**Az owners tábla adatai:** 
- `name` varchar (unique)
- `city` varchar
- `max_number_of_pets` int

**Például:**

| name | city | max_number_of_pets |
|----------|---------|-----------|
| John Johnson | Budapest | 4 |
| John Doe | Boston | 2 |
| Clark Kent | New York | 2 |
| Mark Rover | Békásmegyer | 5 |
| Anne Small | Amsterdam | 1 |

**A pets tábla adatai:**   
- `name` varchar (unique)
- `species` varchar
- `age` int
- `current_owner` varchar

**Például:**

| name | species | age | current_owner |
| ------- | ------- | ---- | ------------- |
| Cica | cat | 1 | John Johnson |
| Bogyo | dog | 3 | John Doe |
| Katty | cat | 1 | Anne Small |
| Pamacs | dog | 4 | Mark Rover |
| Smile | Axolotl | 10 |Mark Rover |
 
FONTOS: Nem kell adatbázist elindítani vagy létrehozni. Az adatbázist a teszt esetek automatikusan létrehozzák és feltöltik adatokkal, nektek csak kapcsolódni kell hozzá a megadott adatok alapján (DB_URL, USERNAME, PASSWORD) és lekérdezni belőle a szükséges adatokat.

## **Java alkalmazás**  
Hozz létre `Owner` és `Pet` osztályokat megfelelő változókkal, getterekkel, setterekkel, hashcode és equals függvényekkel. (A Pet-ben elég az Owner nevét String-ben tárolni)  
Implementáld a `PetAdministrator` osztályt, ami konstruktor paraméterben megkapja az adatbázis eléréshez szükséges adatokat (url, user, password).  
**Az osztályban hozd létre az alábbi metódusokat:**  
`List<Owner> getOwnersWhoCanAdoptPets()`: adja vissza azoknak a tulajdonosoknak a listáját, akikhez még fér új állat. (kevesebb állat van náluk, mint a megadott maximum)  
`Owner getOwnerWithMostPets()`: adja vissza azt a tulajdonost, akinél a legtöbb állat tartózkodik jelenleg  
`List<Pet> getPetsOrderedByAgeDesc()`: adja vissza az állatokat életkoruk szerint csökkenő sorrendben  
`List<Pet> getPetsOfOwner(Owner owner)`: adja vissza az adott tulajnál lévő állatokat  
Van egy `PetAdministrator(String DbUrl, String DbUser, String DbPassword)` konstruktora.
