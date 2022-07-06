# CommandPermissionManager

Easy way to manage your server's command permission.

<details>
<summary>Why made this plugin? (Enjoy machine translate :P)</summary>
Many users have the need to hide their own server plugins, but some (actually most) poorly made plugins don't like to define plugin permissions in plugin.yml, and prefer to use hasPermission in plugins to determine whether the player has permission , which results in that even those commands that the player does not have permission to execute can be executed by the player, so that the plugin corresponding to the command can be obtained. This is not harmful for general plugins, but it is disastrous for plugins like anti-cheat. In addition, it can become annoying to see a full screen of unnecessary commands when the player presses the TAB key. That's why I made this plugin.
</details>

## How to use?
1. Download and install this plugin.
2. Start the server.
3. Configure plugin's config, add any commands and permission you need.
4. Restart the server(recommend) or type "/cpm reload"

## Commands and permissions
| Command | Description | Permission |
| ---------------------------- | ---------------------- | ---------- |
| /commandpermissionmanager reload  | Reload config | commandpermissionmanager.command |

| Permission | Description | Default |
| ---------------------------- | ---------------------- | ---------- |
| commandpermissionmanager.command  | Allow use /commandpermissionmanager | OP |
