#include <chrono>
#include <atomic>
#include <mutex>
#include <iostream>
#include <vector>
#include <thread>

using namespace std;

class Contador
{
    int cont1 = 0;
    atomic<int> cont2 = 0;
    mutex cerrojo;

public:
    void incMutex()
    {
        cerrojo.lock();
        cont1++;
        cerrojo.unlock();
    }

    void incAtomic()
    {
        cont2++;
    }

    void status()
    {
        cout << "contMutex: " << cont1 << " contAtomic: " << cont2 << " ";
    }

};

int main(int argc, char const *argv[])
{
    
    Contador conta;
    vector<thread> hilos;
    chrono::time_point<chrono::system_clock> start, end;


    start = chrono::system_clock::now();
    for(int i = 0; i < 8; i++)
    {
        hilos.push_back(thread([&conta](){
                
                for(int i = 0; i < 10; i++)
                {
                    conta.incAtomic();
                } 
                
        }));
    } 

    for(int i = 0; i < 8; i++)
    {
        hilos[i].join();
    } 

    end = chrono::system_clock::now();

    cout << "------------------------------------------------------------" << endl;
    conta.status();
    cout << "tiempoAtomic: " << (end-start).count() << endl;
    cout << "------------------------------------------------------------" << endl;

    start = chrono::system_clock::now();
    for(int i = 0; i < 8; i++)
    {
        hilos.push_back(thread([&conta](){
                
                for(int i = 0; i < 10; i++)
                {
                    conta.incMutex();
                } 
                
        }));
    } 

    for(int i = 0; i < 8; i++)
    {
        hilos[i+8].join();
    } 

    end = chrono::system_clock::now();

    cout << "------------------------------------------------------------" << endl;
    conta.status();
    cout << "tiempoMutex: " << (end-start).count() << endl;
    cout << "------------------------------------------------------------" << endl;


    return 0;
}