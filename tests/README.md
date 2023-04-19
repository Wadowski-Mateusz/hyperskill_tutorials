# TO-project
Airport services management

Projekt uruchamiany jest poprzez wysyłanie wiadomości poprzez endpointy zawarte w klasach z "Controller" w nazwie.

GeneralManager ma możliwość wysłania wiadomości i czasu przeznaczonego na obsługę samolotu do StandManagera.

StandManager ma możliwość wysłania wiadomości, czasu przeznaczonego na obsługę samolotu do wybranego przez siebie, dostępnego serwisu. Ponadto wysłanie opcji "finished" rozpoczyna procedurę obsługi lotu.

Poszczególne serwisy poprzez wysyłanie wiadomości finished, landed, finisheddeparture albo ready przekazują następnemu w kolejce serwisowi, że może zacząć pracę, w kolejności:

Pilot (landed) -> GeneralManager (send) -> StandManager  (send) -> StandManager (finished) -> LuggageService (finished) -> BoardingService (finished) ->  CleaningService (finished) -> CateringService (finished) -> TankingService (finished) -> BoardingService (finisheddeparture) -> LuggageService (finisheddeparture) -> Pilot (finished) 
-> PushbackService (finished) -> State (ready)


Wzorce strukturalne: Fasada, Most

Wzorce behawioralne: Stan, Metoda Szablonowa, Iterator

Wzorce kreacyjne: Builder, Singleton

Metoda szablonowa - utworzenie abstract flight state zawierającego metody poszczególnych stanów. Konkretne stany nadpisują metodę która dotyczy ich stanu np. PilotState nadpisuje metodę landed(). Jeżeli metoda zostanie użyta w innym stanie pojawia się błąd.

Most - połączenie między GUI a API

Stan - utworzenie stanu dla każdego etapu działania poszczególnych serwisów, utworzenie kolejności zdarzeń - jeżeli aiport service posiada konkretny stan nie możemy w nim wywołać działań dla innch stanów

Fasada - stworzenie rekordu zawierającego wszystkie serwisy dla danego lotu, które przechowywane są w airport service

Builder - budowanie obiektu zawierającego informacje o rodzaju pracy do wykonania

Singleton - bazy danych zdefiniowane jako @Component

Iterator - użycie do przechodzenia po liście WorkOrders w celu znalezienia prac spełniających kryteria iteracji (po numerze lotu)
