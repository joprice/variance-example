
trait Group

class CovariantGroup[+A] extends Group {
    private[this] var members = Seq.empty[A]

    // error: covariant type A occurs in contravariant position in type A of value member
    //def add(member: A): Unit = members :+ member

    def add[B >: A](member: B): Unit = members :+ member

    def first: Option[A] = members.headOption
}

class ContravariantGroup[-A] extends Group {
    private[this] var members = Seq.empty[A]

    // either of these will work
    def add(member: A): Unit = members :+ member
    //def add[B <: A](member: B): Unit = members :+ member

    // error: contravariant type A occurs in covariant position in type  >: A of type B
    //def add[B >: A](member: A): Unit = members :+ member


    // error: contravariant type A occurs in covariant position in type => Option[A] of method first
    //def first: Option[A] = members.headOption
}

class InvariantGroup[A] extends Group {
    private[this] var members = Seq.empty[A]

    def add(member: A): Unit = members :+ member
}

class User
class Moderator extends User
class Admin extends Moderator

object Covariance {
    def main(args: Array[String]) = {
        val covariant = new CovariantGroup[Moderator]()
        covariant.add(new User())
        covariant.add(new Moderator())
        covariant.add(new Admin())

        val contravariant = new ContravariantGroup[Moderator]()
        //contravariant.add(new User()) 
        contravariant.add(new Moderator())
        contravariant.add(new Admin())

        val invariant = new InvariantGroup[Moderator]()
        //invariant.add(new User()) // superclasses of Modreator cannot be added to an invariant type, since Moderator's interface is required
        invariant.add(new Moderator())
        invariant.add(new Admin())

        // Note: Moderator <: User, but class InvariantGroup is invariant in type A. You may wish to define A as +A instead.
        // val invariantUsers: InvariantGroup[User] = invariant
        val invariantModerators: InvariantGroup[Moderator] = invariant
        // Note: Moderator >: Admin, but class InvariantGroup is invariant in type A. You may wish to define A as -A instead.
        // val invariantAdmins: InvariantGroup[Admin] = invariant

        val covariantUsers: CovariantGroup[User] = covariant
        val covariantModerators: CovariantGroup[Moderator] = covariant
        // val covariantAdmins: CovariantGroup[Admin] = covariant

        // val contravariantUsers: ContravariantGroup[User] = contravariant
        val contravariantModerators: ContravariantGroup[Moderator] = contravariant
        val contravariantAdmins: ContravariantGroup[Admin] = contravariant
    }
}

