package org.example.ormdemo.services.exceptions

class MigrationFolderNotFoundException: RuntimeException("Migration folder `db/migration` not found in resources")