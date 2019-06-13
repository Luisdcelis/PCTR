#include <thread>
#include <mutex>
#include <iostream>
#include <vector>


/* struct Comun
{
    int q;
    std::mutex clang1, clang2, clang3;
    std::recursive_mutex clang;

    Comun() : q(0) {}

    void oper_A(int x)
    {
        std::lock_guard<std::recursive_mutex> cerrojo(clang);
        q -= x;
    }

    void oper_B(int x)
    {
        std::lock_guard<std::recursive_mutex> cerrojo(clang);
        q++;
    }
    void oper_C(int x, int y)
    {
        std::lock_guard<std::recursive_mutex> cerrojo(clang);
        oper_B(33);
        oper_A(56);
    }
};

int main()
{
    Comun ref;
    ref.oper_B(5);
    ref.oper_C(6,6);
    std::cout << "Spock dice: Larga vida y prosperidad...";
    return 0;
} */


// ──────────────────────────────────────────────────────────────────────────

/* void hola()
{
    std::cout << "hola desde el hilo" << std::this_thread::get_id() << std::endl;
}

int main()
{
    std::vector<std::thread> hilos;

    for (int i = 0; i < 5; i++)
    {
        hilos.push_back(std::thread(hola));
    }

    for (int i = 0; i < 5; i++)
    {
        hilos[i].join();
    }

    return 0;
}
 */
// con funcion lambda:

/* int main()
{
    std::vector<std::thread> hilos;

    for (int i = 0; i < 5; i++)
    {
        hilos.push_back(std::thread([](){
            std::cout << "hola desde el hilo" << std::this_thread::get_id() << std::endl;
        }));
    }

    for (int i = 0; i < 5; i++)
    {
        hilos[i].join();
    }
    
    return 0;
} */



/* struct Contador 
{ 
    int valor=0;
    std::mutex cerrojo;
    void incrementar ()
    {
        std::lock_guard<std::mutex> cerro(cerrojo);
        ++valor;
    }
};

int main()
{
    Contador contador;
    std::vector<std::thread> hilos;

    for (int i = 0; i < 5; i++)
    {
        hilos.push_back(std::thread([&contador](){
            for (int i = 0; i < 100; i++)
            {
                contador.incrementar();
            }
            
        }));
    }

    for (int i = 0; i < 5; i++)
    {
        hilos[i].join();
    }

    std::cout << contador.valor << std::endl;
    
    return 0;
}
 */

