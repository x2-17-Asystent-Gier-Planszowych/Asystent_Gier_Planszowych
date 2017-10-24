-- Table: "Users"

-- DROP TABLE "Users";

CREATE TABLE "Users"
(
  "Id" serial NOT NULL,
  "Username" text NOT NULL,
  "Email" text NOT NULL,
  "Password" text NOT NULL,
  "About" text,
  "Avatar" bytea,
  "Active" boolean,
  CONSTRAINT "PK_USER" PRIMARY KEY ("Id"),
  CONSTRAINT "Email_Unique" UNIQUE ("Email"),
  CONSTRAINT "Userame_Unique" UNIQUE ("Username")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Users"
  OWNER TO postgres;
-- Table: "Groups"

-- DROP TABLE "Groups";

CREATE TABLE "Groups"
(
  "Id" serial NOT NULL,
  "Groupname" text NOT NULL,
  "Active" boolean,
  CONSTRAINT "PK_GROUP" PRIMARY KEY ("Id"),
  CONSTRAINT "Group_Unique" UNIQUE ("Groupname")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Groups"
  OWNER TO postgres;

  -- Table: "Group_User"

-- DROP TABLE "Group_User";

CREATE TABLE "Group_User"
(
  "Group_Id" serial NOT NULL,
  "User_Id" serial NOT NULL,
  CONSTRAINT "PK_GR_US" PRIMARY KEY ("Group_Id", "User_Id"),
  CONSTRAINT "FK_Group" FOREIGN KEY ("Group_Id")
      REFERENCES "Groups" ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_User" FOREIGN KEY ("User_Id")
      REFERENCES "Users" ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Group_User"
  OWNER TO postgres;
-- Table: "Categories"

-- DROP TABLE "Categories";

CREATE TABLE "Categories"
(
  "Id" serial NOT NULL,
  "Catname" text NOT NULL,
  "Active" boolean,
  CONSTRAINT "PK_CATEGORY" PRIMARY KEY ("Id"),
  CONSTRAINT "Category_Unique" UNIQUE ("Catname")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Categories"
  OWNER TO postgres;
-- Table: "Games"

-- DROP TABLE "Games";

CREATE TABLE "Games"
(
  "Id" serial NOT NULL,
  "Name" text NOT NULL,
  "Active" boolean,
  CONSTRAINT "PK_GAME" PRIMARY KEY ("Id"),
  CONSTRAINT "Game_Unique" UNIQUE ("Name")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Games"
  OWNER TO postgres;
-- Table: "Category_Game"

-- DROP TABLE "Category_Game";

CREATE TABLE "Category_Game"
(
  "Category_Id" serial NOT NULL,
  "Game_Id" serial NOT NULL,
  CONSTRAINT "PK_CA_NA" PRIMARY KEY ("Category_Id", "Game_Id"),
  CONSTRAINT "FK_Cat" FOREIGN KEY ("Category_Id")
      REFERENCES "Categories" ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_Game" FOREIGN KEY ("Game_Id")
      REFERENCES "Games" ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Category_Game"
  OWNER TO postgres;
