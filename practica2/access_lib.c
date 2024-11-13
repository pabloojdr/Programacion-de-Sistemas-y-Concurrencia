/*
============================================================================
 Name        : access_lib.h
 Author      : Pablo Julián Campoy Fernández
 Version     : 1
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "access_lib.h"

int hash(const char *nombre, int size);

void inicialize(struct Person *ptrArray[], int size){
    for(int i = 0; i < size; i++){
        ptrArray[i] = NULL;
    }
}

void addPerson(struct Person *ptrArray[], int size, const char * nombre, const char * huella, int *ok){ // hay que ir iterando con el siguiente no por el mismo
    int pos = hash(nombre, size);
    struct Person *aux = malloc(sizeof(struct Person));
    if(aux == NULL){
     *ok = 0;   
    }
    if(ptrArray[pos] == NULL){
        aux->nombre = strdup(nombre);
        aux->huella = strdup(huella);
        aux->siguiente = NULL;
        ptrArray[pos] = aux;
        *ok = 1;
        printf("No había ningún elemento en la posición destinada. Se ha añadido a %s en la posición %i.\n", nombre, pos);
    } else {
        struct Person *aux2 = ptrArray[pos];
        int comp = strcmp(aux2->huella, huella);
        while (comp < 0){
            if(aux2->siguiente == NULL)
                break;
            comp = strcmp(aux2->huella, huella);
            if(comp > 0)
                break;
            aux2 = aux2->siguiente;
        }
        comp = strcmp(aux2->huella, huella);
        if(comp > 0){
            aux->nombre = strdup(aux2->nombre);
            aux->huella = strdup(aux2->huella);
            aux->siguiente = aux2->siguiente;
            aux2->nombre = strdup(nombre);
            aux2->huella = strdup(huella);
            aux2->siguiente = aux;
            *ok = 1;
        } else if(comp < 0){
            aux->nombre = strdup(nombre);
            aux->huella = strdup(huella);
            aux->siguiente = aux2->siguiente;
            aux2->siguiente = aux;
            printf("Se ha insertado de forma ordenada a %s en la posicion %i.\n", nombre, pos);
            *ok = 1;
        } else {
            *ok = 0;
            printf("La persona con nombre %s y huella %s ya se encontraba en la lista.\n", nombre, huella);
        }
    }
  }

void borrarNodo(struct Person *nodo){
    if(nodo != NULL){
        struct Person *aux = nodo;
        while(aux != NULL){
            nodo = nodo->siguiente;
            free(aux->huella);
            free(aux->nombre);
            free(aux);
            aux = nodo;
        }
        nodo = NULL;
    }
}

void destruir(struct Person *ptrArray[], int size){
    for(int i = 0; i < size; i++){
        borrarNodo(ptrArray[i]);
        ptrArray[i] = NULL;
    }
}

void saveTextFile(char * fileName, struct Person *ptrArray[], int size){
    FILE *pfile = fopen(fileName, "wt");
    if(pfile == NULL){
        printf("No se ha podido abrir el fichero proporcionado: %s.\n", fileName);
    }
    for(int i = 0; i < size; i++){
        struct Person *temp = ptrArray[i];
        if(temp != NULL){
            while(temp != NULL){
                fprintf(pfile, "%s;%s\n", temp->nombre, temp->huella);
                temp = temp->siguiente;
            }
        }
    }
    fclose(pfile);
    printf("Se han escrito los datos en el archivo correctamente.\n");
}

void saveBinaryFile(char * fileName, struct Person *ptrArray[], int size){
    FILE *pfile = fopen(fileName, "wb");
    if(pfile == NULL){
        printf("No se ha podido abrir el fichero proporcionado: %s.\n", fileName);
    }
    for(int i = 0; i < size; i++){
        struct Person *temp = ptrArray[i];
        if(temp != NULL){
            while(temp != NULL){
                int tam = strlen(temp->nombre) + 1;
                int tam2 = strlen(temp->huella) + 1;
                fwrite(&tam, sizeof(int), 1, pfile);
                fwrite("\n", sizeof(char), 1, pfile);
                fwrite(temp->nombre, sizeof(char), tam - 1, pfile);
                fwrite("\n", sizeof(char), 1, pfile);
                fwrite(&tam2, sizeof(int), 1, pfile);
                fwrite("\n", sizeof(char), 1, pfile);
                fwrite(temp->huella, sizeof(char), tam2 - 1, pfile);
                fwrite("\n", sizeof(char), 1, pfile);
                temp = temp->siguiente;
            }
        }
    }
    fclose(pfile);
    printf("Se han escrito los datos en el archivo correctamente.\n");
}

void loadTextFile(char * fileName, struct Person *ptrArray[], int size){
    destruir(ptrArray, size);
    FILE *pfile = fopen(fileName, "rt");
    if(pfile == NULL){
        printf("No se ha podido abrir el fichero proporcionado: %s.\n", fileName);
    }
    char *nombre = malloc(512 * sizeof(char)); // Asignar memoria para nombre
    char *huella = malloc(512 * sizeof(char)); // Asignar memoria para huella
    if (nombre == NULL || huella == NULL) {
        printf("Error: No se pudo asignar memoria para los elementos auxiliares.\n");
        fclose(pfile);
        return;
    }
    int ok;
    while(fscanf(pfile, "%99[^;];%99[^\n]\n", nombre, huella) == 2){
        addPerson(ptrArray, size, nombre, huella, &ok);
    }
    fclose(pfile);
    printf("Se han leido los datos del archivo correctamente.\n");
    free(huella);
    free(nombre);
}

void loadBinaryFile(char * fileName, struct Person *ptrArray[], int size){
    destruir(ptrArray, size);
    
    FILE *pfile = fopen(fileName, "rb");
    
    if(pfile == NULL){
        printf("No se ha podido abrir el fichero proporcionado: %s.\n", fileName);
    }

    int ok;
    int tam;
    char *salto; 
    while(fread(&tam, sizeof(int), 1, pfile) == 1){
        char *nombre = malloc(tam * sizeof(char));
        if(nombre == NULL){
            printf("No se ha podido asignar memoria para almacenar el nombre.\n");
            fclose(pfile);
            return;
        }
        if(fgetc(pfile) != '\n'){
            printf("Error al leer el salto de linea.\n");
            free(nombre);
            fclose(pfile);
            return;
        }
        // Leemos el nombre correspondiente
        if(fread(nombre, sizeof(char), tam, pfile) != tam){
            printf("Ha habido un error leyendo un nombre.\n");
            free(nombre);
            fclose(pfile);
            return;
        }
        nombre[tam-1] = '\0'; // Debemos añadir el carácter terminador al final de la cadena

        // Leemos el tamaño de la huella
        if(fread(&tam, sizeof(int), 1, pfile) != 1){
            printf("Ha habido un error leyendo el tamaño de la huella.\n");
            free(nombre);
            fclose(pfile);
            return;
        }
        char *huella = malloc(tam * sizeof(char));
         if(huella == NULL){
            printf("No se ha podido asignar memoria para almacenar la huella.\n");
            free(nombre);
            fclose(pfile);
            return;
        }

        // Leemos el salto de línea
        if(fgetc(pfile) != '\n'){
            printf("Error al leer el salto de linea.\n");
            free(nombre);
            free(huella);
            fclose(pfile);
            return;
        }

        //Leemos la huella
        if(fread(huella, sizeof(char), tam, pfile) != tam){
            printf("Ha habido un error leyendo la huella.\n");
            free(nombre);
            free(huella);
            fclose(pfile);
            return;
        }
        huella[tam-1] = '\0';
        addPerson(ptrArray, size, nombre, huella, &ok);
        free(nombre);
        free(huella);
    }
    fclose(pfile);
    printf("La lectura del archivo en binario se ha realizado con exito.\n");
}

int autorize(struct Person *ptrArray[], int size, const char * nombre, const char * huella){
    int pos = hash(nombre, size);
    int res = 0;
    struct Person *aux = malloc(sizeof(struct Person));
    aux = ptrArray[pos];
    while(aux != NULL){
        if(strcmp(aux->nombre, nombre) == 0 && strcmp(aux->huella, huella) == 0)
            return pos;
        aux = aux->siguiente;
    }
    return 0;
}

void removePerson(struct Person *ptrArray[], int size, const char * nombre, const char * huella, int *ok){
    int pos = autorize(ptrArray, size, nombre, huella);
    if(pos != 0){
        struct Person *aux = malloc(sizeof(struct Person));
        while(ptrArray[pos] != NULL){
            aux = ptrArray[pos]->siguiente;
            if(aux != NULL){
                if(strcmp(aux->nombre, nombre) == 0 && strcmp(aux->huella, huella) == 0){
                    ptrArray[pos]->siguiente = aux->siguiente;
                    free(aux->nombre);
                    free(aux->huella);
                    free(aux);
                    *ok = 1;
                    return;
                }
            } else {
                if(strcmp(ptrArray[pos]->nombre, nombre) == 0 && strcmp(ptrArray[pos]->huella, huella) == 0){
                    free(ptrArray[pos]->nombre);
                    free(ptrArray[pos]->huella);
                    free(ptrArray[pos]);
                    ptrArray[pos] = NULL;
                    *ok = 1;
                    return;
                }
            }
            ptrArray[pos] = ptrArray[pos]->siguiente;
        }
    }
    *ok = 0;
}

//Zona privada, no se incluye en *.h
int hash(const char *nombre, int size) { // Se usa muchísimo la función para dar rapidez
    int sum = 0;
    const char *ptr = nombre;
    
    while (*ptr != '\0') {
        sum += *ptr;
        ptr++;
    }
    
    return sum % (size); // Devuelve un número entre 0 y N-1
}

