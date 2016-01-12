package evil.ducks.monocle

import monocle.macros.GenLens
import monocle.Lens

case class Street(name: String)
case class Address(number: Int, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

object MyLens {
  val _company = Lens[Employee, Company](_.company)(c => e => e.copy(company = c))
  val _address = Lens[Company, Address](_.address)(a => c => c.copy(address = a))
  val _street = Lens[Address, Street](_.street)(s => a => a.copy(street = s))

  import scala.language.higherKinds
  val _number = GenLens[Address](_.number)

  val _companyAddress = _company composeLens _address
  val _companyStreet = _companyAddress ^|-> _street
}

object Main extends App {
  import MyLens._
  println("Playing with monocles")

  val acaciaAvenue = Street("Acacia Avenue")
  val evilDucksBuilding = Address(22, acaciaAvenue)
  val evilDucks = Company("Evil Ducks", evilDucksBuilding)
  val tony = Employee("Tony", evilDucks)

  println(tony)
  println(s"${tony.name}'s company is ${_company.get(tony)}")
  println(s"The address for ${evilDucks.name} is ${_address.get(evilDucks)}")
  println(s"$evilDucksBuilding is at number ${_number.get(evilDucksBuilding)} on ${_street.get(evilDucksBuilding)}")
  println(s"${tony.name}'s company address is ${_companyAddress.get(tony)}")
  println(s"${tony.name}'s company is on ${_companyStreet.get(tony)}")

  println("Can we update something?")
  val bakerStreet = Address(101, Street("Baker Street"))

  val moveTo = (address: Address) => _companyAddress.modify(_ => address)
  println(moveTo(bakerStreet)(tony))
}
