### EntityManager

EntityManager это интерфейс, который описывает API для всех основных операций над Enitity, получение данных и
других сущностей JPA. По сути главный API для работы с JPA.

**Основные операции:**
1) **Для операций над Entity:** persist (добавление Entity под управление JPA), merge (обновление), remove (удаления),
   refresh (обновление данных), detach (удаление из управление JPA), lock (блокирование Entity от изменений в
   других thread);
2) **Получение данных:** find (поиск и получение Entity), createQuery, createNamedQuery, createNativeQuery, contains,
   createNamedStoredProcedureQuery, createStoredProcedureQuery;
3) **Получение других сущностей JPA:** getTransaction, getEntityManagerFactory, getCriteriaBuilder, getMetamodel,
   getDelegate;
4) **Работа с EntityGraph:** createEntityGraph, getEntityGraph;
4) **Общие операции над EntityManager или всеми Entities:** close, isOpen, getProperties, setProperty, clear;
