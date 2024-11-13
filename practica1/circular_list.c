/*
============================================================================
 Name        : circular_list.h
 Author      : Pablo Julián Campoy Fernández
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#include "circular_list.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

void iniciar(TListaPtr *ptrNuevaLista){
    *ptrNuevaLista = NULL;
}

void addDato(TListaPtr *ptrLista, const char *dato, int *ok)
{
    TListaPtr aux = malloc(sizeof(struct CList_Nodo));
    if(aux == NULL){
        *ok = 0;
        printf("No se pudo asignar memoria para el nodo auxiliar.\n");
        return;
    }

    aux->dato = strdup(dato);
    if(aux->dato == NULL){
        *ok = 0;
        printf("No se pudo asignar memoria para el dato '%s'.\n", dato);
        free(aux); // Liberar la memoria asignada para el nodo auxiliar
        return;
    }
    aux->sig = NULL;

    if (*ptrLista == NULL){
        *ptrLista = aux;
        (*ptrLista)->sig = *ptrLista;
        *ok = 1;
        printf("El puntero a lista estaba vacio. Se agregi el dato '%s' al inicio.\n", dato);
    } else {
        TListaPtr aux2 = *ptrLista;
        aux->sig = aux2->sig;
        (*ptrLista)->sig = aux;
        *ptrLista = aux;
        *ok = 1;
        printf("Se agrego el dato '%s' al final de la lista.\n", dato);
    }
}

void mostrar(TListaPtr lista){
    if(lista == NULL){
        printf("La lista está vacía.\n");
        return;
    }

    TListaPtr actual = lista->sig; // Puntero para recorrer la lista. Apunta al siguiente elemento porque al añadir un nuevo elemento a la lista esta apunta al último añadido
    printf("%s\n", actual->dato); // Imprime el primer dato

    actual = actual->sig; // Avanzamos en la lista

    while(actual != lista->sig){
        printf("%s\n", actual->dato);
        actual = actual->sig;
    }
}

void extraerDato(TListaPtr *ptrLista, char ** datoExtraido, int *ok){
    TListaPtr aux = malloc(sizeof(struct CList_Nodo));
    if(aux == NULL){
        *ok = 0;
        printf("No se pudo asignar memoria para extraer el dato. \n");
    }

    if(*ptrLista == NULL){
        *ok = 0;
        printf("La lista proporcionada esta vacia.\n");
    } else if((*ptrLista)->sig == (*ptrLista)){
        *datoExtraido = strdup((*ptrLista)->dato);
        printf("Se ha extraido el siguiente dato: %s.\n", *datoExtraido);
        free(*ptrLista); 
        *ptrLista = NULL;
        *ok = 1;
    } else {
        aux = (*ptrLista)->sig;
        (*ptrLista)->sig = (*ptrLista)->sig->sig;
        *datoExtraido = aux->dato;
        printf("Se ha extraido el siguiente dato: %s.\n", *datoExtraido);
        free(aux);
        *ok = 1;
    }
}

void destruir(TListaPtr *ptrLista){
        TListaPtr aux = malloc(sizeof(struct CList_Nodo));
        *ptrLista = (*ptrLista)->sig;
        while((*ptrLista) != NULL){
            char *extraido;
            int ok;
            extraerDato(ptrLista, &extraido, &ok);
        }
    }