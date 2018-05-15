package net.ddns.kiro47.dynamictrees;

public enum Permissions
{


    MAIN_COMMAND("cmd.main");

    private String permission;

    Permissions(String permission)
    {
        this.permission = permission;
    }

    public String getPermission()
    {
        return "dynamictrees." + permission;
    }

}
