package com.nammamistri.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile SiteDao _siteDao;

  private volatile MaterialLogDao _materialLogDao;

  private volatile WorkerDao _workerDao;

  private volatile WageEntryDao _wageEntryDao;

  private volatile MaterialRateDao _materialRateDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `sites` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `location` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `isActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `material_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `siteId` INTEGER NOT NULL, `bricks` INTEGER NOT NULL, `cementBags` INTEGER NOT NULL, `sandLoads` REAL NOT NULL, `wallLength` REAL NOT NULL, `wallWidth` REAL NOT NULL, `wallHeight` REAL NOT NULL, `wallThickness` TEXT NOT NULL, `calculatedOn` INTEGER NOT NULL, FOREIGN KEY(`siteId`) REFERENCES `sites`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_material_logs_siteId` ON `material_logs` (`siteId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `workers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `siteId` INTEGER NOT NULL, `name` TEXT NOT NULL, `dailyWage` REAL NOT NULL, `phoneNumber` TEXT NOT NULL, `joiningDate` INTEGER NOT NULL, FOREIGN KEY(`siteId`) REFERENCES `sites`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_workers_siteId` ON `workers` (`siteId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `wage_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `workerId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `isPresent` INTEGER NOT NULL, `advancePayment` REAL NOT NULL, `notes` TEXT NOT NULL, FOREIGN KEY(`workerId`) REFERENCES `workers`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_wage_entries_workerId` ON `wage_entries` (`workerId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `material_rates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `materialName` TEXT NOT NULL, `unit` TEXT NOT NULL, `pricePerUnit` REAL NOT NULL, `lastUpdated` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1213122ec0ce3f412d13f6c58027f2ed')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `sites`");
        db.execSQL("DROP TABLE IF EXISTS `material_logs`");
        db.execSQL("DROP TABLE IF EXISTS `workers`");
        db.execSQL("DROP TABLE IF EXISTS `wage_entries`");
        db.execSQL("DROP TABLE IF EXISTS `material_rates`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSites = new HashMap<String, TableInfo.Column>(5);
        _columnsSites.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSites.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSites.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSites.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSites.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSites = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSites = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSites = new TableInfo("sites", _columnsSites, _foreignKeysSites, _indicesSites);
        final TableInfo _existingSites = TableInfo.read(db, "sites");
        if (!_infoSites.equals(_existingSites)) {
          return new RoomOpenHelper.ValidationResult(false, "sites(com.nammamistri.app.data.model.Site).\n"
                  + " Expected:\n" + _infoSites + "\n"
                  + " Found:\n" + _existingSites);
        }
        final HashMap<String, TableInfo.Column> _columnsMaterialLogs = new HashMap<String, TableInfo.Column>(10);
        _columnsMaterialLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("siteId", new TableInfo.Column("siteId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("bricks", new TableInfo.Column("bricks", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("cementBags", new TableInfo.Column("cementBags", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("sandLoads", new TableInfo.Column("sandLoads", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("wallLength", new TableInfo.Column("wallLength", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("wallWidth", new TableInfo.Column("wallWidth", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("wallHeight", new TableInfo.Column("wallHeight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("wallThickness", new TableInfo.Column("wallThickness", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialLogs.put("calculatedOn", new TableInfo.Column("calculatedOn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMaterialLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMaterialLogs.add(new TableInfo.ForeignKey("sites", "CASCADE", "NO ACTION", Arrays.asList("siteId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMaterialLogs = new HashSet<TableInfo.Index>(1);
        _indicesMaterialLogs.add(new TableInfo.Index("index_material_logs_siteId", false, Arrays.asList("siteId"), Arrays.asList("ASC")));
        final TableInfo _infoMaterialLogs = new TableInfo("material_logs", _columnsMaterialLogs, _foreignKeysMaterialLogs, _indicesMaterialLogs);
        final TableInfo _existingMaterialLogs = TableInfo.read(db, "material_logs");
        if (!_infoMaterialLogs.equals(_existingMaterialLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "material_logs(com.nammamistri.app.data.model.MaterialLog).\n"
                  + " Expected:\n" + _infoMaterialLogs + "\n"
                  + " Found:\n" + _existingMaterialLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkers = new HashMap<String, TableInfo.Column>(6);
        _columnsWorkers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("siteId", new TableInfo.Column("siteId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("dailyWage", new TableInfo.Column("dailyWage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("joiningDate", new TableInfo.Column("joiningDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkers = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWorkers.add(new TableInfo.ForeignKey("sites", "CASCADE", "NO ACTION", Arrays.asList("siteId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesWorkers = new HashSet<TableInfo.Index>(1);
        _indicesWorkers.add(new TableInfo.Index("index_workers_siteId", false, Arrays.asList("siteId"), Arrays.asList("ASC")));
        final TableInfo _infoWorkers = new TableInfo("workers", _columnsWorkers, _foreignKeysWorkers, _indicesWorkers);
        final TableInfo _existingWorkers = TableInfo.read(db, "workers");
        if (!_infoWorkers.equals(_existingWorkers)) {
          return new RoomOpenHelper.ValidationResult(false, "workers(com.nammamistri.app.data.model.Worker).\n"
                  + " Expected:\n" + _infoWorkers + "\n"
                  + " Found:\n" + _existingWorkers);
        }
        final HashMap<String, TableInfo.Column> _columnsWageEntries = new HashMap<String, TableInfo.Column>(6);
        _columnsWageEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWageEntries.put("workerId", new TableInfo.Column("workerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWageEntries.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWageEntries.put("isPresent", new TableInfo.Column("isPresent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWageEntries.put("advancePayment", new TableInfo.Column("advancePayment", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWageEntries.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWageEntries = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWageEntries.add(new TableInfo.ForeignKey("workers", "CASCADE", "NO ACTION", Arrays.asList("workerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesWageEntries = new HashSet<TableInfo.Index>(1);
        _indicesWageEntries.add(new TableInfo.Index("index_wage_entries_workerId", false, Arrays.asList("workerId"), Arrays.asList("ASC")));
        final TableInfo _infoWageEntries = new TableInfo("wage_entries", _columnsWageEntries, _foreignKeysWageEntries, _indicesWageEntries);
        final TableInfo _existingWageEntries = TableInfo.read(db, "wage_entries");
        if (!_infoWageEntries.equals(_existingWageEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "wage_entries(com.nammamistri.app.data.model.WageEntry).\n"
                  + " Expected:\n" + _infoWageEntries + "\n"
                  + " Found:\n" + _existingWageEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsMaterialRates = new HashMap<String, TableInfo.Column>(5);
        _columnsMaterialRates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialRates.put("materialName", new TableInfo.Column("materialName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialRates.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialRates.put("pricePerUnit", new TableInfo.Column("pricePerUnit", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterialRates.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMaterialRates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMaterialRates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMaterialRates = new TableInfo("material_rates", _columnsMaterialRates, _foreignKeysMaterialRates, _indicesMaterialRates);
        final TableInfo _existingMaterialRates = TableInfo.read(db, "material_rates");
        if (!_infoMaterialRates.equals(_existingMaterialRates)) {
          return new RoomOpenHelper.ValidationResult(false, "material_rates(com.nammamistri.app.data.model.MaterialRate).\n"
                  + " Expected:\n" + _infoMaterialRates + "\n"
                  + " Found:\n" + _existingMaterialRates);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1213122ec0ce3f412d13f6c58027f2ed", "7385165f94f6ba55fba1922f29b35113");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "sites","material_logs","workers","wage_entries","material_rates");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `sites`");
      _db.execSQL("DELETE FROM `material_logs`");
      _db.execSQL("DELETE FROM `workers`");
      _db.execSQL("DELETE FROM `wage_entries`");
      _db.execSQL("DELETE FROM `material_rates`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SiteDao.class, SiteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MaterialLogDao.class, MaterialLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkerDao.class, WorkerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WageEntryDao.class, WageEntryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MaterialRateDao.class, MaterialRateDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SiteDao siteDao() {
    if (_siteDao != null) {
      return _siteDao;
    } else {
      synchronized(this) {
        if(_siteDao == null) {
          _siteDao = new SiteDao_Impl(this);
        }
        return _siteDao;
      }
    }
  }

  @Override
  public MaterialLogDao materialLogDao() {
    if (_materialLogDao != null) {
      return _materialLogDao;
    } else {
      synchronized(this) {
        if(_materialLogDao == null) {
          _materialLogDao = new MaterialLogDao_Impl(this);
        }
        return _materialLogDao;
      }
    }
  }

  @Override
  public WorkerDao workerDao() {
    if (_workerDao != null) {
      return _workerDao;
    } else {
      synchronized(this) {
        if(_workerDao == null) {
          _workerDao = new WorkerDao_Impl(this);
        }
        return _workerDao;
      }
    }
  }

  @Override
  public WageEntryDao wageEntryDao() {
    if (_wageEntryDao != null) {
      return _wageEntryDao;
    } else {
      synchronized(this) {
        if(_wageEntryDao == null) {
          _wageEntryDao = new WageEntryDao_Impl(this);
        }
        return _wageEntryDao;
      }
    }
  }

  @Override
  public MaterialRateDao materialRateDao() {
    if (_materialRateDao != null) {
      return _materialRateDao;
    } else {
      synchronized(this) {
        if(_materialRateDao == null) {
          _materialRateDao = new MaterialRateDao_Impl(this);
        }
        return _materialRateDao;
      }
    }
  }
}
