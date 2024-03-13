ZADANIE 1
Utwórz zupełnie nowego agenta, który będzie wyszukiwał słowo w innym słowniku na stronie http://www.dict.org .

Ins
1. Dodaj Nową Klase mozna skopiować z ServiceAgent (w nowym pliku *java)
2. Dodaj Nowe Zachowanie, można skopiować z MyDictonaryBehaviour
3. zmień typ przesyłany do konstruktora
4. dodaj ontologie i słownik
5. Pamietaj o dodaniu argumentu do build.xlm 
6. wszystkie zmiany są otagowane TODO:[ZAD2]

Jak Sprawdzić:
- Uruchom program.
- PPM -> UserAgent -> Send Message
- ustaw:
  * Comminicative act: request
  * Ontology: nazwa ktrórą wpisaleś wyzej (u mnie new_agent_dict)
  * content: słowo które chcesz przetłumaczyć.
- kliknije ok -> ok -> powinno pojawic sie przetlumczone slowo po niemiecku