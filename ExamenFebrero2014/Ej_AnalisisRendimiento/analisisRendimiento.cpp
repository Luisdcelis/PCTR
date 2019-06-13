#include <mutex>
#include <atomic>
#include <chrono>
#include <ctime>
#include <iostream>
#include <vector>
#include <thread>

using namespace std;

struct contador
{
    int contador;
    mutex cerrojo;
    atomic<int> atomico;
    void incMutex()
    {
        cerrojo.lock();
        contador++;
        cerrojo.unlock();
    }
    void incAtomic()
    {
        atomico++;
    }
};

int main(){
    contador c;
    vector<thread> hilos;
    int numeroIteraciones = 1000;
    int numeroExperimentos = 1;
    chrono::time_point<chrono::system_clock> start, end;
    chrono::duration<double> tiempo;
    while(numeroExperimentos < 10){
        //Crear y lanzar hebras
        start = chrono::system_clock::now();
        for(int i = 0;i < 10;++i){
            hilos.push_back(thread([&c, &numeroExperimentos](){
                for(int j = 0;j < (numeroExperimentos + 1) * 1000;++j){
                    c.incMutex();
                }
            }));
        }
        for(int i = 0;i < 10;++i){
            hilos[i].join();
        }
        end = chrono::system_clock::now();
        tiempo = end - start;
        cout<<"Para el experimento "<<numeroExperimentos * 1000<<"\n" <<tiempo.count()<<endl;   //Tiempo con mutex
        hilos.clear();
        //Crear y lanzar hebras
        start = chrono::system_clock::now();
        for(int i = 0;i < 10;++i){
            hilos.push_back(thread([&c, &numeroExperimentos](){
                for(int j = 0;j < (numeroExperimentos + 1) * 1000;++j){
                    c.incAtomic();
                }
            }));
        }
        for(int i = 0;i < 10;++i){
            hilos[i].join();
        }
        end = chrono::system_clock::now();
        hilos.clear();
        tiempo = end - start;
        cout<<tiempo.count()<<endl;   //Tiempo con Atomic
        
        ++numeroExperimentos;
    }
}