# Groundhog Engine
A Simple engine/interpreter and scripting language format to describe characters and their
interaction as a scripted movie while keeping world ready for any external change that can affect that behaviours,
an an expected manner.


### Main concepts
You start by describing a world. everything in this world is either an item or character or the world itself.
And everything that needs to be in any way interactive needs to have a brain/script attached to it.
Obviously one thing that NEED's to have script is world, in which you initialize other characters/items.
Then you create scripts for each item or character you need.


### Groundhog Script - GS
Any item/character or world is described with a .gs file, and has 3 important components to it.
* Agenda - agenda is the main code part from which everything starts, it contains a script that runs first.
* State - one script can have many states that perform some kind of macro action. Like character is in "going_home" state, or character is in "eating_food" state.
State can have it's [main] body that executes when state starts, or trigger body.
* Triggers - every state can have one or many triggers that execute their code when certain event is happening.

### GS Main structure
GS consists of 2 parts, the main "structure" that just describes states and triggers and agendas, and the "actual" executable script snippets inside that parts.
The main structure is described with tags made in brackets, like this:

```
[agenda]
    ...
[/agenda]
[state "example_state"]
    [main]
        ...
    [/main
[/state]
[state "example_state2"]
    [trigger ...]
        ...
    [/trigger]
[/state]
```

### GS Inner syntax
The inner script with actual commands is very simple scripting language, it does one command per line,
first world in line is command name, and rest is arguments divided with spaces. Each commands starts only
when previous command is finished, so it may take a while before command is finished.
Here is a simple hello world example script

```
[agenda]
    change_state example_state
[/agenda]
[state "example_state"]
    [main]
        say "Hello World!"
    [/main
[/state]
```

### GS Commands list
I am going to add more commands as we go, here is the current list:

* **delay 4** - does nothing for 4 seconds
* **say "Hello Pretty"** - Outputs a text, this takes time depending on how long text is.
* **change_state new_state** - Halts current state and executes another one
* **walk_to_position position** - walks to a coordinate specified in a variable
* **walk_to_object world.items.itemA** - walks to a specific item, if item moves, it will follow.
* **operation stats.variable = 10** - performs a simple operation with variables/expressions
* **condition_start** stats.variable == 10 - performs a conditional check on variable/expression
* **condition_end** - ends the condition
* **marker aaa** - marks this line with marker
* **goto aaa** - sends code execution to the previously marked code line, to perform looping
* **init_person bob 0:0** - creates a person, puts it in 0,0 coordinate and tries to load it's bob.gs script
* **init_item table 0:0** - creates an item, puts it in 0,0 coordinate and tries to laod it's table.hs script
* **item_interact world.items.tv turn_on** - let's tv that turn_on action was performed on it.
* **fire_event earthquake_start** - fires event with specific name


### GS Triggers
Triggers are parts of state that contain a simple code snippets, that execute only when specific event is taking place.
There are 3 types of things trigger can react to:
* **value** - when a specific variable has changed
* **interaction** - when a specific interaction was done to an item
* **event** - when specific global event was fired.

Here is an example code:

```
[trigger value world.items.table.leg_count]
    // this script will execute when leg_count of item called table has been changed.
[/trigger]
[trigger interaction tv.turn_on]
    // this script will execute when someone called item_interact world.items.tv turn_on
[/trigger]
[trigger event earthquake_start]
    // this script will execute when global event earthquake_start has been fired with fire_event command
[/trigger]
```


### GS Variables/Scopes/Expressions

There is are things like variables, scopes and expressions in GS.
By default each variable is looked in local scope of executing character/item.
if an external variable needs to be accessed we can change scrope to world scope by doing
"world.". World scope contains "items" and "people" scopes. which contain list of corresponding characters/items.
So for character named "jake" to access health of character named "bob" we need to type world.people.bob.health,
if we type just "health" it will be the local health variable of "jake" instead.
all vairable names are made up, meaning there is no need for pre-initialisation, any variable you
type, will be created if it did not exist. So you can easily make up something like:
stats.bla.bla.bla.health and it will work. The dot notation expressions/variables are used in operation or condition commands.
Or in any other commands attribute where you need it.
If your character needs to remember something, you can make up a virtual "memory" scope and use it like this:

```
operation memory.some_var = 10
```

Here are some other examples:

```
operation world.people.jake.stats.hp -= 10
```

```
condition_start stats.hp < 0
    say "okay I am dead now..."
condition_end
```


### External Inputs

Currently there is just one way to interact with world externally, by clicking on items with mouse. When clicked
an item interaction event button_press will be called, which you can catch with a trigger.


### Where to from now on?

Go ahead and experiment with GS, and make some cool interactive stories. The idea is to make a scripting language
that can allow you to write less, while still keeping characters being able to "react" to any "undescribed" situation.