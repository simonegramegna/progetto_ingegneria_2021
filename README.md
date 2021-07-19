<h1>INDICE</h1>

1. Introduzione

2. Modello di dominio

3. Requisiti specifici

4. OO Design

   1. Diagrammi delle classi e di sequenza

6. Riepilogo del test

7. Manuale utente

8. Processo di sviluppo e organizzazione del lavoro

9. Analisi retrospettiva

   1. Cosa ci ha fatto sentire soddisfatti

   2. Cosa ci ha fatto sentire insoddisfatti

   3. Cosa ci ha fatto impazzire


<h2>Introduzione</h2>

Gioco tradizionale dalle origini antichissime (le prime tracce risalgono all'Egitto del XV secolo a.C.), la dama è uno dei giochi da tavolo di strategia più giocati al mondo; a sostegno di questa tesi, basti pensare che ne esistono almeno 9 varianti tra le quali ricordiamo la dama inglese, giocata soprattutto nel Regno Unito e negli USA, la dama spagnola e quella internazionale (o polacca).



In questo progetto, svoltosi nell'ambito del corso di Ingegneria del Software nell'a.a. 2020/21 all'Uniba, le regole seguite sono quelle della dama italiana che prevede una damiera 8x8, 12 pedine per ciascuno dei 2 giocatori e la possibilità per le stesse di muoversi solo in avanti. Vi sono anche altre regole come l'obbligo per ogni pedina che può farlo di mangiare una pedina avversaria che però esulano dallo scopo del progetto e pertanto si è deciso di non inserirle. Al momento della consegna, è possibile muovere tutte le pedine secondo le regole della dama italiana, mangiare le pedine avversarie e creare delle dame. Inoltre, dando all'interno dell'applicazione alcuni comandi (rimandiamo per i dettagli al Manuale Utente) è possibile mostrare a schermo il tempo passato per ciascun giocatore, le mosse che sono state eseguite nel loro ordine di esecuzione e quante prese sono state effettuate dai giocatori.



Questo progetto è stato realizzato dai componenti del team stroustrup:

- Federico Antonio Pio Canistro
- Fabio Abbondanza
- Simone Gramegna
- Matteo Inglese

<h2>Modello di dominio</h2>

![SaveTokenInSecret 1](../res/img/report/7.jpeg)

<h2>Requisiti specifici</h2>

* <h5>Mostrare l'help con elenco comandi</h5>

  Criteri di accettazione:

  Eseguendo il comando `help` o invocando l'app con `--help` o `-h` il risultato è una descrizione concisa, che normalmente appare all'avvio del programma seguita dalla lista dei comandi disponibili, uno per riga:

  ![SaveTokenInSecret 2](../res/img/report/photo5884073756815635862.jpg)

* <h5>Iniziare una nuova partita</h5>

  Criteri di accettazione:

  Al comando `gioca`

  * se nessuna partita è in corso, l'app si predispone a ricevere la prima mossa di gioco o altri comandi

    ![SaveTokenInSecret 3](../res/img/report/photo5884073756815635863.jpg)

  

* <h5>Abbandonare la partita</h5>

  Criteri di accettazione:

  Al comando `abbandona`

  * l'app chiede conferma

  * se la conferma è positiva, l'app comunica che il Bianco (o il Nero) ha vinto per abbandono

  * se la conferma è negativa, l'app si predispone a ricevere nuovi comandi

    ![SaveTokenInSecret 4](../res/img/report/photo5884073756815635856.jpg)

    ![SaveTokenInSecret 5](../res/img/report/photo5884073756815635855.jpg)

    ![SaveTokenInSecret 6](../res/img/report/photo5884073756815635864.jpg)

* <h5>Chiudere il gioco</h5>

  Criteri di accettazione:

  Al comandi `esci`

  * l'applicazione chiede conferma 

  * se la conferma è positiva, l'app si chiude restituendo un zero exit code

  * se la conferma è negativa, l'app si predispone a ricevere nuovi comandi

    ![SaveTokenInSecret 7](../res/img/report/photo5884073756815635861.jpg)

  ![SaveTokenInSecret 8](../res/img/report/photo5884073756815635860.jpg)

* <h5>Mostrare la damiera con la numerazione</h5>

  Criteri di accettazione:

  Al comando `numeri`

  * l'app mostra la damiera con i numeri sulle caselle nere

    ![SaveTokenInSecret 9](../res/img/report/photo5884073756815635853.jpg)

  

* <h5>Mostrare la damiera con i pezzi</h5>

  Criteri di accettazione:

  Al comando `damiera`

  * se il gioco è iniziato, l'app mostra la posizione di tutti i pezzi sulla damiera; i pezzi sono mostrati in formato Unicode
  * se il gioco non è iniziato, l'app suggerisce il comando `gioca`

  ![SaveTokenInSecret 10](../res/img/report/photo5884073756815635852.jpg)

  

* <h5>Mostrare il tempo di gioco</h5>

  Criteri di accettazione:

  Al comando `tempo` 

  * se il gioco è in corso

    * l'app mostra il tempo trascorso per il Bianco dall'inizio del gioco
    * l'app mostra il tempo trascorso per il Nero dall'inizio del gioco

  * se il gioco non è in corso l'app suggerisce il comando gioca e si predispone a ricevere nuovi comandi

  
  ![SaveTokenInSecret 11](../res/img/report/photo5884073756815635854.jpg)
  
* <h5>Spostare una pedina (spostamento semplice)</h5>

  Criteri di accettazione:

  * a partita in corso, l'app deve accettare mosse di spostamento semplice di pedina in notazione algebrica (es. 1-5)

  * Lo spostamento semplice della pedina deve rispettare le 
    regole del gioco della dama italiana, escludendo 
    damature e prese:
    http://www.fid.it/regolamenti/capo1.htm
    In particolare (Art. 4 - Gli spostamenti semplici)
    La pedina può essere mossa solo in avanti e in diagonale e portata dalla casella di partenza in una casella libera contigua.
    
    ![SaveTokenInSecret 12](../res/img/report/photo5884073756815635857.jpg)

* <h5>Spostare una pedina con presa semplice</h5>

  Criteri di accettazione:

  - a partita in corso di gioco, l'app deve accettare mosse di 
    spostamento di pedina con presa semplice in notazione 
    algebrica.
    Es. 18x11 (se è il bianco a muovere)
    
  - Lo spostamento della pedina con presa semplice deve 
    rispettare le regole del gioco della dama italiana
    (http://www.fid.it/regolamenti/capo1.htm)
    In particolare (Art. 5 e 6)
    
    ![SaveTokenInSecret 13](../res/img/report/photo5884073756815635859.jpg)

* <h5>Spostare una pedina con presa multipla</h5>

  Criteri di accettazione:

  - a partita in corso di gioco, l'app deve accettare mosse di 
    spostamento di pedina con presa multipla in notazione 
    algebrica. 
    Es. 22x15x6 (se è il bianco a muovere)
    
  - Lo spostamento della pedina con presa multipla deve 
    rispettare le regole del gioco della dama italiana
    (http://www.fid.it/regolamenti/capo1.htm)
    In particolare (Art. 5 e 6)
    
    ![SaveTokenInSecret 14](../res/img/report/photo5884073756815635858.jpg)

* <h5>Spostare una pedina con damatura</h5>

  Criteri di accettazione:

  - a partita in corso di gioco, l'app deve accettare mosse di 
    spostamento semplice di pedina in notazione algebrica 
    che terminano con la damatura.
    Es. 6-3 oppure se c'è una presa 10x3
    
  - Lo spostamento con damatura deve rispettare le regole 
    del gioco della dama italiana : 
    http://www.fid.it/regolamenti/capo1.htm
    
    ![SaveTokenInSecret 15](../res/img/report/photo5884073756815635865.jpg)

* <h5>Mostrare le prese</h5>

  Criteri di accettazione:

  Al comando `prese` l'app mostra le prese del Bianco e del Nero con caratteri Unicode
  Es.
  Bianco: ⛂ ⛂
  Nero: ⛀ ⛀ ⛀ ⛀ 

  ![SaveTokenInSecret 16](../res/img/report/photo5884073756815635866.jpg)

* <h5>Mostrare le mosse giocate</h5>

  Criteri di accettazione:

  Al comando `mosse` l'app mostra la storia delle mosse con notazione algebrica
  Esempio:
  B 22-18
  N 11-14
  B 23-20
  N 12x16
  ...

  ![SaveTokenInSecret 17](../res/img/report/photo5884073756815635867.jpg)



<h2>OO Design</h2>

In questa sezione riportiamo il diagramma delle classi e i diagrammi di sequenza di alcune user story significative.

<h5>Diagramma delle classi</h5>

![SaveTokenInSecret 18](../res/img/report/3.jpeg)



<h5>Diagramma della user story 3 (abbandonare la partita)</h5>

![SaveTokenInSecret 19](../res/img/report/5.jpeg)



<h5>Diagramma della user story 8 (spostamento semplice)</h5>

![SaveTokenInSecret 20](../res/img/report/1.jpeg)

<h5>Diagramma della user story 9 (presa semplice)</h5>

![SaveTokenInSecret 21](../res/img/report/2.jpeg)

<h2>Riepilogo dei test</h2>

![SaveTokenInSecret 22](../res/img/report/test1.PNG)

![SaveTokenInSecret 23](../res/img/report/testClassi.PNG)

![SaveTokenInSecret 24](../res/img/report/jacoco.PNG)

![SaveTokenInSecret 25](../res/img/report/6.jpeg)

Riportiamo alcuni warning di Spotbugs che non ci è stato possibile risolvere. In particolare il Deadstore presente nella classe Player sulla variabile "total" è dovuto al fatto che per Spotbugs la variabile non viene utilizzata, mentre è aggiornata ogni volta che la funzione viene richiamata.

![SaveTokenInSecret 26](../res/img/report/spotbugs.PNG)

<h2>Manuale utente</h2>

* L'esecuzione dell'applicazione avviene all'interno di un container docker tramite il comando
  * `docker run --rm -it docker.pkg.github.com/softeng2021-inf-uniba/progetto2021ter-stroustrup/dama-stroustrup:latest`
* E' possibile anche richiamare l'help all'avvio dell'app passando i parametri `-h` o `--help` nel seguente modo:
  * `docker run --rm -it docker.pkg.github.com/softeng2021-inf-uniba/progetto2021ter-stroustrup/dama-stroustrup:latest -h`
  * `docker run --rm -it docker.pkg.github.com/softeng2021-inf-uniba/progetto2021ter-stroustrup/dama-stroustrup:latest --help`
* Una volta avviato il programma è possibile iniziare una nuova partita con il comando `gioca`. Una volta iniziata una partita è possibile accedere ad altri comandi e funzionalità:
  * Con il comando `damiera` viene mostrata la damiera con le pedine
  * Con il comando `numeri` viene mostrata la damiera con i numeri sulle caselle nere 
  * Si possono effettuare delle mosse usando la notazione algebrica  (es. 23-19), delle prese semplici (es. 18x11) e delle prese multiple (es. 22x15x6)
  * Con il comando `tempo` viene mostrato il tempo trascorso in partita dai giocatori
  * Con il comando `mosse` vengono mostrate le mosse eseguite durante la partita nell'ordine in cui sono state effettuate
  * Con il comando `prese` vengono mostrate le pedine che sono state mangiate dai giocatori
  * Con il comando `abbandona` (e dopo aver confermato) la partita in corso si chiude dando la vittoria al giocatore ancora in partita
* Per chiudere l'applicazione è sufficiente usare il comando `esci` e confermare

<h2>Processo di sviluppo e organizzazione del lavoro</h2>

Per quanto riguarda l'organizzazione del lavoro si è scelto di adottare una metodologia agile di sviluppo in stile **Scrum** nella quale il professore del corso svolgeva il ruolo di ***product owner*** facendosi portavoce di un cliente fittizio. L'intero progetto è stato poi diviso in 4 *Sprint*, ciascuno con degli obiettivi specifici da raggiungere e un ***product backlog* **di features da elaborare sulla base di una ***Definition of Done***, rispettando il principio del lavoro agile che "impone" di favorire la produzione di codice funzionante.



Per ogni user story presente nel product backlog degli Sprint è stato rispettato il **GitHub Flow**, creando un'issue da assegnare ad uno o più membri del Team che, dopo aver lavorato su un branch separato dal master e superata una fase di testing, hanno potuto effettuare il push dei cambiamenti apportati al progetto aprendo una pull request. A questo punto, alcuni membri del gruppo hanno potuto revisionare il lavoro svolto e, dopo aver dato esplicita conferma, hanno consentito il merge sul branch principale.



Ciascuna issue è passata tra cinque fasi distinte che ci hanno permesso di tenere facilmente traccia del lavoro svolto e dello stato generale di avanzamento del progetto; possiamo distinguere tali fasi come segue:

* **TO DO**

  * L'issue è aperta e il lavoro sulla stessa non è ancora cominciato

* **IN PROGRESS**

  * Il lavoro sull'issue è iniziato ed è in fase di sviluppo

* **REVIEW**

  * Il lavoro sull'issue è in fase di revisione da parte di uno o più membri del Team

* **READY**

  * Il lavoro è stato revisionato ma non ancora del tutto approvato

* **DONE**

  * L'issue è chiusa e non è necessario effettuare ulteriori modifiche

  

Dal punto di vista del Team di sviluppo, abbiamo cercato di dare priorità alla creazione di un ambiente di lavoro soddisfacente che non facesse sentire nessun membro del team particolarmente oppresso e distribuendo il carico di lavoro sulla base della disponibilità e delle capacità di ognuno, valorizzando (a nostro modo di vedere) le caratteristiche individuali dei membri e spingendoci a lavorare serenamente senza lasciare indietro nessuno. Un approccio simile è stato adottato anche nelle scelte organizzative delle figure "apicali" del team, preferendo non elevare ufficialmente alcuno studente al ruolo di *ScrumMaster*, ma scambiandoci spesso il ruolo di facilitatori del processo di sviluppo, confrontandoci e portando suggerimenti e critiche costruttive al codice e alle scelte di design del progetto ad ogni incontro del Team.

<h2>Analisi retrospettiva</h2>

Dopo aver lavorato a questo progetto per diverse settimane, tante e variegate sono le sensazioni e i pensieri che ci vengono in mente ripensando a quanto è stato fatto e al percorso che abbiamo intrapreso. Abbiamo provato a riassumere gli alti e (forse soprattutto) i bassi nelle poche righe che seguono, consapevoli dell'opportunità che un progetto come questo (sia pure molto semplice) rappresenta, specialmente per chi, come la maggior parte dei membri del nostro Team, non ha mai lavorato in gruppo utilizzando metodologie e processi di sviluppo agili.

<h4>Cosa ci ha fatto sentire soddisfatti</h4>

Come è stato più volte sottolineato all'interno di questo report, lo sviluppo tramite Scrum ci ha consentito un grado di libertà che poche altre realtà consentono e questo già basterebbe per ritenersi soddisfatti; tuttavia quello che più ci è piaciuto è stata la possibilità di poter variare (anche di molto) ruoli e tipologia di problemi da risolvere sapendo di poter contare sull'aiuto e la revisione degli altri membri, cosa che ci ha consentito non solo di migliorare (specie nelle molte sessioni di *pair programming*) in quei campi in cui presentavamo lacune, ma anche di coordinarci meglio come team. 



Grande soddisfazione, inoltre, ci è stata data dall'utilizzo di strumenti moderni ed efficaci come GitHub e Docker che, una volta padroneggiati, consentono di mantenere il proprio lavoro ordinato e limitano i potenziali problemi (di sviluppo e sicurezza) in cui è possibile incorrere.

<h4>Cosa ci ha fatti sentire insoddisfatti</h4>

Se da un lato la fluidità dei ruoli e delle mansioni da svolgere è stato fondamentale per organizzare le ore di lavoro, dall'altro organizzarsi e coordinarsi per far funzionare al meglio questa macchina che è il nostro team, ha richiesto uno sforzo maggiore del previsto. Specialmente nel corso dello Sprint 1 (e in parte nello Sprint 2) la voglia di chiudere velocemente alcune issue per passare ad altre ci ha fatto perdere di vista il quadro generale portandoci a seguire in maniera frettolosa il GitHub Flow e facendoci commettere errori grossolani che potevano essere evitati. Se non altro abbiamo imparato che la fretta è una pessima consigliera e che rispettare i tempi e i limiti di tutti i membri del gruppo è un passaggio per nulla secondario al fine di produrre con costanza del codice funzionante e correttamente testato e revisionato.

<h4>Cosa ci ha fatto impazzire</h4>

Innumerevoli sono le volte che ci siamo trovati a mostrare tutto il nostro rancore nei confronti dell'ambiente di sviluppo Eclipse. Tra errori inesistenti che impedivano la compilazione (prontamente risolti col caro vecchio chiudi e riapri) e situazioni con i file di testing che rasentavano il paranormale, ci siamo più volte sentiti frustrati nell'utilizzo dell'IDE cosa che, sebbene alla fine non ci abbia limitato, ci ha fatto sicuramente perdere più ore del dovuto risultando alle volte macchinoso e di difficile gestione. E' vero che è nelle situazioni meno favorevoli che si cresce di più, ma qualche problema di meno ci avrebbe senz'altro fatto comodo.
