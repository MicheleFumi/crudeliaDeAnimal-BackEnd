
    create table animale (
        id integer not null auto_increment,
        id_utente integer not null,
        nome_animale varchar(100) not null,
        razza varchar(100) not null,
        tipo varchar(100) not null,
        note_mediche varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table carrelli (
        id integer not null auto_increment,
        utente_id integer not null,
        stato_ordine enum ('CANCELLATO','NUOVO','ORDINATO') not null,
        primary key (id)
    ) engine=InnoDB;

    create table carrello_prodotti (
        carrello_id integer not null,
        id integer not null auto_increment,
        prodotto_id integer not null,
        quantita_richieste integer,
        stato_ordine enum ('CANCELLATO','CREATO','MODIFICATO') not null,
        primary key (id)
    ) engine=InnoDB;

    create table messaggi_systema (
        code varchar(255) not null,
        lang varchar(255) not null,
        messaggio varchar(255),
        primary key (code, lang)
    ) engine=InnoDB;

    create table ordine (
        carrello_id integer not null,
        data_ordine date not null,
        id integer not null auto_increment,
        id_utente integer not null,
        totale_ordine decimal(10,2) not null,
        stato_ordine enum ('CANCELLATO','NUOVO','ORDINATO') not null,
        primary key (id)
    ) engine=InnoDB;

    create table ordine_prodotto (
        id integer not null auto_increment,
        id_ordine integer not null,
        id_prodotto integer not null,
        id_utente integer not null,
        quantita integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table prenotazione_visita (
        data_visita date not null,
        id integer not null auto_increment,
        id_animale integer not null,
        id_utente integer not null,
        id_veterinario integer not null,
        ora_visita time(6) not null,
        motivo_visita varchar(255) not null,
        stato_visita enum ('ANNULLATA','CONFERMATA','IN_LAVORAZIONE') not null,
        tipo_pagamento enum ('CARTA','CONTANTI','PAYPAL') not null,
        primary key (id)
    ) engine=InnoDB;

    create table `prodotto-animali` (
        id integer not null auto_increment,
        prezzo decimal(10,2) not null,
        quantita_disponibile integer not null,
        categoria varchar(100) not null,
        tipo_animale varchar(100) not null,
        immagine_url varchar(500),
        descrizione varchar(255),
        nome_prodotto varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table utente (
        data_registrazione date not null,
        id integer not null auto_increment,
        codice_fiscale varchar(16) not null,
        cognome varchar(255) not null,
        email varchar(255) not null,
        indirizzo varchar(255) not null,
        nome varchar(255) not null,
        password varchar(255) not null,
        telefono varchar(255) not null,
        role enum ('ADMIN','MEDICO','USER') not null,
        primary key (id)
    ) engine=InnoDB;

    create table `veterinario-ospedali` (
        id integer not null auto_increment,
        id_utente integer,
        telefono varchar(20) not null,
        cap varchar(255) not null,
        email varchar(255) not null,
        indirizzo varchar(255) not null,
        nome varchar(255) not null,
        orari_apertura varchar(255) not null,
        provincia varchar(255) not null,
        regione varchar(255) not null,
        servizi_offerti enum ('ANALISI_DEL_SANGUE','CHIRURGIA','DAY_HOSPITAL','DERMATOLOGIA','ODONTOIATRIA','OFTALMOLOGIA','ONCOLOGIA','ORTOPEDICA','PRONTO_SOCCORSO','RADIOGRAFIE','VACCINAZIONI','VISITE_GENERALI') not null,
        strutture_sanitarie enum ('CLINICA','OSPEDALE') not null,
        primary key (id)
    ) engine=InnoDB;

    alter table carrelli 
       add constraint UKni131g1h3vvjgmsmp4r36iogf unique (utente_id);

    alter table utente 
       add constraint UK3vd7df1l1hnmhukq2bembt26n unique (codice_fiscale);

    alter table animale 
       add constraint FKpf8p7i5wdxiprfjjnpasqg0gr 
       foreign key (id_utente) 
       references utente (id);

    alter table carrelli 
       add constraint FKhye9ul5d25dx4fx9fu8fvvdv 
       foreign key (utente_id) 
       references utente (id);

    alter table carrello_prodotti 
       add constraint FK4y0wuftxn6ll90g9udap5nvwc 
       foreign key (carrello_id) 
       references carrelli (id);

    alter table carrello_prodotti 
       add constraint FK6gm4qt0jnwgfd1ny5littdtbp 
       foreign key (prodotto_id) 
       references `prodotto-animali` (id);

    alter table ordine 
       add constraint FKt3ku9cmrfyqbdsq4khd04qvyw 
       foreign key (carrello_id) 
       references carrelli (id);

    alter table ordine 
       add constraint FKgsxxfj3dm1kfppteavqrvkwcr 
       foreign key (id_utente) 
       references utente (id);

    alter table ordine_prodotto 
       add constraint FK4vjw61obacst8xlbui1b7c1wr 
       foreign key (id_ordine) 
       references ordine (id);

    alter table ordine_prodotto 
       add constraint FKckes7nsoopbs4gjsxeekuwknu 
       foreign key (id_prodotto) 
       references `prodotto-animali` (id);

    alter table ordine_prodotto 
       add constraint FKc0xlqewkm3w4x7yy9ejd2bxi8 
       foreign key (id_utente) 
       references utente (id);

    alter table prenotazione_visita 
       add constraint FKk8jsyvh3fovv8qyoon6nkthwd 
       foreign key (id_animale) 
       references animale (id);

    alter table prenotazione_visita 
       add constraint FK9gkmmq069tjmhsr0daemvkbw9 
       foreign key (id_utente) 
       references utente (id);

    alter table prenotazione_visita 
       add constraint FKp23s68frh403uulxkep6dcbu0 
       foreign key (id_veterinario) 
       references `veterinario-ospedali` (id);

    alter table `veterinario-ospedali` 
       add constraint FK9sq3ef870hfbhsccnm6aifekq 
       foreign key (id_utente) 
       references utente (id);
