Example Story:

Jake, Bob and Phill are standing in the dark nothingness. In front of them are 2 boxes, Box A and Box B.
Box A has button to turn on the light, and Box B has botton to turn off the light.
Bob and Jake have no plans at all and are just standing there doing nothing, and they like dark. 
Jake was thinking for some time to turn on the lights, so he goes to Box A and turns it on. Then he walked back.
Phill likes dark, so he noticed that lights are on walked to Box B and turned them off.
Bob noticed that lights went dark again, and said "Thanks... [burp]"
Jake again walked to Box A and turned lights ON. 
Phill was tired already so he just stayed where he is doing nothing. Bob did nothing as well.
Story Ended.


Micro Commands
1) walk towards item.
2) interract with item (set a key/value)
3) say something out load
4) walk towards coordinate.
5) remember key/value
6) add to inner stat (key value)
7) delay X seconds
8) go to marker
9) change remote item value (set a key/value)


micro command syntax:

SCOPES:
-------------------
entity{
	values - set from engine, and also can be set from outside
	world - everyone can see world and it's properties
}
human extends entity {
	stats - stats like exhaustion irritation e.g.
	memory - like values but can only be set by human itself (as private field)
}
entities {
	boxA
	boxB
	jake
	bob
}


MICRO_COMMANDS:
----------------------
operation
condition_start
condition_end
say
change_state
marker
delay
walk_to_item
item_interact
goto
walk_to_position


bob.s
--------
[agenda]
    change_state do_nothing
[/agenda]
[state "do_nothing"]
	[trigger world.light on]
		operation stats.irritation += 20
		condition_start stats.irritation > 50
			say "srsly?... [burp]"
		condition_end
	[/trigger]
	[trigger world.light off]
		operation stats.irritation -= 10
		say "Thanks... [burp]"
	[/trigger]
[/state]




jake.s
--------
[agenda]
	change_state do_nothing
[/agenda]
[state "turn_lights_on"]
	[main]
		condition_start world.light == on
			change_state do_nothing
		condition_end
		operation memory.last_position = (values.curr_position)
		marker 1
		walk_to_item entities.boxA
		item_interact entities.boxA.values.pressed true
		condition_start world.light == off
			delay 2000
			operation stats.irritation += 10
			condition_start stats.irritation > 50
				goto 2
			condition_end	
			goto 1
		condition_end
		marker 2
		walk_to_position memory.last_position
		change_state do_nothing
	[/main]
[/state]
[state "do_nothing"]
	[trigger value state.time_passed == 5]
		change_state turn_lights_on
	[trigger]
	[trigger value world.light == off]
		change_state turn_lights_on
	[trigger]
[/state]


phil.s
--------
[agenda]
	change_state do_nothing
[/agenda]
[state "turn_lights_off"]
	[main]
		condition_start world.light == off
			change_state do_nothing
		condition_end
		condition_start stats.exhaustion > 0
			change_state do_nothing
		condition_end		
		operation memory.last_position = (values.curr_position)
		marker 1
		walk_to_item entities.boxB
		operation stats.exhaustion += 10
		item_interact entities.boxB.values.pressed true
		condition_start world.light == on
			delay 2000
			operation stats.irritation += 10
			condition_start stats.irritation > 50
				goto 2
			condition_end	
			goto 1
		condition_end
		marker 2
		walk_to_position memory.last_position
		change_state do_nothing
	[/main]
[/state]
[state "do_nothing"]
	[trigger value state.time_passed == 5]
		change_state turn_lights_on
	[trigger]
	[trigger value world.light == off]
		change_state turn_lights_on
	[trigger]
[/state]


box_a.s
--------
[state "do_nothing"]
	[trigger value values.pressed true]
		operation values.pressed = false
		operation world.light = on
	[trigger]
[/state]

box_b.s
--------
[state "do_nothing"]
	[trigger value values.pressed true]
		operation values.pressed = false
		operation world.light = off
	[trigger]
[/state]


global_macro_commands.s
-------------------------
[state "do_nothing"]
    [main]
    [/main]
[/state]


Here is how interpreting and comiling works

First Script reader separates data into agendas, states and triggers.
Then it runs and finds all micro scripts insode triggers agendas or main blocks, and compiles them into
machine readable data. and also compiles main scripts into linked data.

