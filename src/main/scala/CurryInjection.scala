import collection.mutable.HashMap

case class Stuff(name: String)

trait Repository {
  def read(name: String): Stuff
  def write(thing: Stuff): Unit
}

trait StuffService {
  val getStuff: Repository => String => Stuff = {
    repo =>
      name =>
        repo.read(name)
  }
  val putStuff: Repository => Stuff => Unit = {
    repo =>
      thing =>
        repo.write(thing)
  }
}

object HashMapRepository {
  val repository = new HashMapRepository
}

class HashMapRepository extends Repository {
  private val things: HashMap[String, Stuff] = new HashMap[String, Stuff]

  def read(name: String) = {
    things(name)
  }

  def write(thing: Stuff) {
    things += (thing.name -> thing)
  }
}

object StuffMapService extends StuffService {
  import HashMapRepository._

  val fetchStuff = getStuff(repository)
  val writeStuff = putStuff(repository)
}

object Tester extends App {
  import StuffMapService._

  val myStuff = Stuff("nate")
  writeStuff(myStuff)

  val fetched = fetchStuff("nate")
  println(fetched)
}