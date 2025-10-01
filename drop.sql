
    alter table animale 
       drop 
       foreign key FKpf8p7i5wdxiprfjjnpasqg0gr;

    alter table carrelli 
       drop 
       foreign key FKhye9ul5d25dx4fx9fu8fvvdv;

    alter table carrello_prodotti 
       drop 
       foreign key FK4y0wuftxn6ll90g9udap5nvwc;

    alter table carrello_prodotti 
       drop 
       foreign key FK6gm4qt0jnwgfd1ny5littdtbp;

    alter table ordine 
       drop 
       foreign key FKt3ku9cmrfyqbdsq4khd04qvyw;

    alter table ordine 
       drop 
       foreign key FKgsxxfj3dm1kfppteavqrvkwcr;

    alter table ordine_prodotto 
       drop 
       foreign key FK4vjw61obacst8xlbui1b7c1wr;

    alter table ordine_prodotto 
       drop 
       foreign key FKckes7nsoopbs4gjsxeekuwknu;

    alter table prenotazione_visita 
       drop 
       foreign key FKk8jsyvh3fovv8qyoon6nkthwd;

    alter table prenotazione_visita 
       drop 
       foreign key FK9gkmmq069tjmhsr0daemvkbw9;

    alter table prenotazione_visita 
       drop 
       foreign key FKp23s68frh403uulxkep6dcbu0;

    alter table `veterinario-ospedali` 
       drop 
       foreign key FK9sq3ef870hfbhsccnm6aifekq;

    drop table if exists animale;

    drop table if exists carrelli;

    drop table if exists carrello_prodotti;

    drop table if exists messaggi_systema;

    drop table if exists ordine;

    drop table if exists ordine_prodotto;

    drop table if exists prenotazione_visita;

    drop table if exists `prodotto-animali`;

    drop table if exists utente;

    drop table if exists `veterinario-ospedali`;
