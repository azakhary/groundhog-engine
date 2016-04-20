[agenda]
    change_state talk_test
[/agenda]
[state "talk_test"]
	[main]
		delay 1
		say "...[burp]"
		delay 1
		walk_to_item world.items.lamp.position
		say "Yippie Ki Yay MotherFucker"
	[/main]
[/state]