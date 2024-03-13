ZADANIE 1
Utwórz dodatkową usługę agenta, jako nowe zachowanie zarejestrowane w konfiguracji ServiceAgent , które będzie wyszukiwało słowo w innym słowniku na stronie http://www.dict.org .

Anty plagiat:

1. na https://dict.org/bin/Dict zbadaj element słownika który chcesz użyć
2. zmień fd-pol-ita na własny słownik który chcesz użyć, z pkt wyżej
3. zmień my_dict na własną nazwe(uzywana w ontology)
4. zmień wszystkie nazwy klasy MyDictonaryCyclicBehaviour na własne

Jak Sprawdzić:
- Uruchom program.
- PPM -> UserAgent -> Send Message
- ustaw:
  * Comminicative act: request
  * Ontology: nazwa ktrórą wpisaleś wyzej (u mnie my_dict)
  * content: słowo które chcesz przetłumaczyć.
- kliknije ok -> ok -> powinno pojawic sie przetlumczone slowo 