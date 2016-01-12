package evil.ducks.monocle

import monocle.Lens

case class Street(name: String)
case class Address(number: Int, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

object Main extends App {
  println("Playing with monocles")

  val acaciaAvenue = Street("Acacia Avenue")
  val evilDucksBuilding = Address(22, acaciaAvenue)
  val evilDucks = Company("Evil Ducks", evilDucksBuilding)
  val tony = Employee("Tony", evilDucks)

  println(tony)

  val _company = Lens[Employee, Company](_.company)(c => e => e.copy(company = c))
  println(s"${tony.name}'s company is ${_company.get(tony)}")

  val _address = Lens[Company, Address](_.address)(a => c => c.copy(address = a))
  println(s"The address for ${evilDucks.name} is ${_address.get(evilDucks)}")

  val _street = Lens[Address, Street](_.street)(s => a => a.copy(street = s))
  val _number = Lens[Address, Int](_.number)(n => a => a.copy(number = n))
  println(s"$evilDucksBuilding is at number ${_number.get(evilDucksBuilding)} on ${_street.get(evilDucksBuilding)}")

  println(s"${tony.name}'s company address is ${(_company composeLens _address).get(tony)}")
  println(s"${tony.name}'s company is on ${(_company ^|-> _address ^|-> _street).get(tony)}")
}
