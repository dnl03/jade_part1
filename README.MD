Zadanie 3 - Połącz wszystkie usługi i zachowania ServiceAgent w jedno. 
Powinieneś mieć jedną klasę agenta i jedną klasę zachowania, które będą w stanie obsłużyć żądania do dowolnego słownika z dict.org. 
Załóżmy, że użytkownik wpisuje nazwę prawdziwego słownika z dict.org (np. fd-eng-spa ).


step1. Należy usunąć przeszukiwanie serwisów za pomocą ontoligii (klasa My Agent)
step1. W MyAgent nalezy szukać usługi o nazwie podanej w Service Agencie (u mnie "all_dict_service")
step2. Należy dodać tylko jedną usługę (service) w ServiceAgencie z nazwą taką jak wyszukiwana w MyAgent (u mnie "all_dict_service")
step3. Należy dodać tylko jedno zachowanie
step4. Należy mieć tylko jedną klase Behaviour
step5. W Behaviour usunąć templatke która matchuje wiadomość po ontology
step6. Należy pobrać ontology z message 
step7. Należy wysłać zapytanie z pobrana wcześniej ontology

Sprawdzanie tak jak w każdym innym przypadku, w ontology wpisujeme tylko nazwe słownika w content wpisuje słowo
przykladowe słowniki:
fd-pol-gle - polsko irlandski
fd-pol-spa - polsko hiszpanski
fd-pol-ita -  polsko włoski
fd-eng-deu - angielsko niemeicki