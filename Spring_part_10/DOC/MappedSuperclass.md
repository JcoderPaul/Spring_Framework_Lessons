### Mapped Superclass

**Mapped Superclass - это класс от которого наследуются Entity**, он может содержать аннотации JPA, однако сам такой
класс не является Entity, ему необязательно выполнять все требования установленные для Entity (например, он может
не содержать первичного ключа). Такой класс не может использоваться в операциях EntityManager или Query. Такой
класс должен быть отмечен аннотацией MappedSuperclass или соответственно описан в *.xml файле.

Пример на JAVA:

```Java
 @MappedSuperclass
 public class Employee {
 
  @Id
  protected Integer empId;
 
  @Version
  protected Integer version;
 
  @ManyToOne @JoinColumn(name="ADDR")
  protected Address address;
 
  public Integer getEmpId() { ... }
  public void setEmpId(Integer id) { ... }
  public Address getAddress() { ... }
  public void setAddress(Address addr) { ... }
 }
 
 // Default table is FTEMPLOYEE table
 @Entity
 public class FTEmployee extends Employee {
  // Inherited empId field mapped to FTEMPLOYEE.EMPID
  // Inherited version field mapped to FTEMPLOYEE.VERSION
  // Inherited address field mapped to FTEMPLOYEE.ADDR fk
  // Defaults to FTEMPLOYEE.SALARY
 protected Integer salary;
 public FTEmployee() {}
 public Integer getSalary() { ... }
 public void setSalary(Integer salary) { ... }
 }
 
 @Entity
 @Table(name="PT_EMP")
 @AssociationOverride(name="address",
 joincolumns=@JoinColumn(name="ADDR_ID"))
 public class PartTimeEmployee extends Employee {
  // Inherited empId field mapped to PT_EMP.EMPID
  // Inherited version field mapped to PT_EMP.VERSION
  // address field mapping overridden to PT_EMP.ADDR_ID fk
  @Column(name="WAGE")
  protected Float hourlyWage;
  public PartTimeEmployee() {}
  public Float getHourlyWage() { ... }
  public void setHourlyWage(Float wage) { ... }
 }
```

---
- Оригинальный док. см. [MappedSuperclass](https://docs.oracle.com/javaee/7/api/javax/persistence/MappedSuperclass.html);
- См. примеры (RUS): [Hibernate_part_3](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_3);
