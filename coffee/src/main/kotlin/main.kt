package machine

enum class Status() { 
    MAIN_MENU_SHOW,
    MAIN_MENU_TAKE_INPUT, 
    BUY_MENU_SHOW, 
    BUY_MENU_TAKE_INPUT, 
    FILL_MENU_1, 
    FILL_MENU_2, 
    FILL_MENU_3, 
    FILL_MENU_4
}

enum class Coffee(val water: Int, val milk: Int, val coffeeBeans: Int, val cups: Int, val cost: Int) {
    ESPRESSO(250, 0, 16, 1, 4),
    LATTE(350, 75, 20, 1, 7),
    CAPPUCINO(200, 100, 12, 1, 6)
}

class CoffeeMachine(){
    var currentCups: Int = 9
    var currentCoffee: Int = 120
    var currentWater: Int = 400
    var currentMilk: Int = 540
    var currentMoney: Int = 550
    var status: Status = Status.MAIN_MENU_SHOW
    val MENU_MASSAGE = "Write action (buy, fill, take, remaining, exit): "
    
    public fun ui(choice: String) {
        
        when(this.status) {
            Status.MAIN_MENU_SHOW -> {
                println(MENU_MASSAGE)
                this.status = Status.MAIN_MENU_TAKE_INPUT
            }
            Status.MAIN_MENU_TAKE_INPUT -> {
                when(choice) {
                    "buy" -> {
                        println("")
                        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
                        this.status = Status.BUY_MENU_TAKE_INPUT
                    }
                    "fill" -> {
                        println("")
                        println("Write how many ml of water you want to add: ")
                        this.status = Status.FILL_MENU_1
                    }
                    "take" -> { 
                        println("")
                        take()
                        println(MENU_MASSAGE)
                    }
                    "remaining" -> {
                        println("")
                        remaining()
                        println(MENU_MASSAGE)
                    }
                    "exit" -> throw Exception()
                }
            }
            // Status.BUY_MENU_SHOW -> {
            //     this.status = Status.BUY_MENU_TAKE_INPUT
            // }
            Status.BUY_MENU_TAKE_INPUT -> {
                buy(choice)
                println("")
                this.status = Status.MAIN_MENU_TAKE_INPUT
            }
            Status.FILL_MENU_1 -> {
                this.currentWater += choice.toInt()
                println("Write how many ml of milk you want to add: ")
                this.status = Status.values()[this.status.ordinal + 1]
            }
            Status.FILL_MENU_2 -> {
                this.currentMilk += choice.toInt()
                println("Write how many grams of coffee beans you want to add: ") 
                this.status = Status.values()[this.status.ordinal + 1]
            }
            Status.FILL_MENU_3 -> {
                this.currentCoffee += choice.toInt()
                println("Write how many disposable cups you want to add: ")
                this.status = Status.values()[this.status.ordinal + 1]
            }
            Status.FILL_MENU_4 -> {
                this.currentCups += choice.toInt()
                println(MENU_MASSAGE)
                this.status = Status.MAIN_MENU_TAKE_INPUT
            }
        }
    }

    private fun buy(choice: String) {

        if (choice == "back") return
        val coffee: Coffee = Coffee.values()[choice.toInt() - 1]
        
        if (currentWater < coffee.water) {
            println("Sorry, not enough water!")
        } else if (currentMilk < coffee.milk){
            println("Sorry, not enough milk!")
        } else if (currentCoffee < coffee.coffeeBeans){
            println("Sorry, not enough coffee!")
        } else if (currentCups < coffee.cups){
            println("Sorry, not enough cups!")
        } else {    
            this.currentWater -= coffee.water
            this.currentMilk -= coffee.milk
            this.currentCoffee -= coffee.coffeeBeans
            this.currentCups -= coffee.cups
            this.currentMoney += coffee.cost
            println("I have enough resources, making you a coffee!")
        }
    }

    private fun remaining() {
        println("""
            The coffee machine has:
            ${this.currentWater} ml of water
            ${this.currentMilk} ml of milk
            ${this.currentCoffee} g of coffee beans
            ${this.currentCups} disposable cups
            $${this.currentMoney} of money

        """.trimIndent())
    }
    
    private fun take() {
        println("I gave you $${this.currentMoney}")
        this.currentMoney = 0
    }
    
}

 
fun main() {
    val machine: CoffeeMachine = CoffeeMachine()
    machine.ui("")
    try {
        while (true)
        // TODO what about printing menu? There will be no input from user
            machine.ui(readln())
    } catch (e: Exception) {
        // just breaking out of the loop
    }
}