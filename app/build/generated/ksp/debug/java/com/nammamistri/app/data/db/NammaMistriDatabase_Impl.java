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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NammaMistriDatabase_Impl extends NammaMistriDatabase {
  private volatile CalculationDao _calculationDao;

  private volatile LaborDao _laborDao;

  private volatile PhotoDao _photoDao;

  private volatile RatesDao _ratesDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `calculations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `projectName` TEXT NOT NULL, `calculationType` TEXT NOT NULL, `length` REAL NOT NULL, `width` REAL NOT NULL, `height` REAL NOT NULL, `area` REAL NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, `materialCost` REAL NOT NULL, `laborCost` REAL NOT NULL, `totalCost` REAL NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `materials` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `calculationId` INTEGER NOT NULL, `name` TEXT NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, `rate` REAL NOT NULL, `amount` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `laborers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `phone` TEXT NOT NULL, `skill` TEXT NOT NULL, `dailyWage` REAL NOT NULL, `isActive` INTEGER NOT NULL, `photoUri` TEXT, `address` TEXT NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `attendance` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `laborId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `projectName` TEXT NOT NULL, `hoursWorked` REAL NOT NULL, `overtimeHours` REAL NOT NULL, `status` TEXT NOT NULL, `wageEarned` REAL NOT NULL, `advancePaid` REAL NOT NULL, `notes` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `payments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `laborId` INTEGER NOT NULL, `amount` REAL NOT NULL, `paymentDate` INTEGER NOT NULL, `paymentMode` TEXT NOT NULL, `description` TEXT NOT NULL, `isAdvance` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `site_photos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `projectName` TEXT NOT NULL, `photoUri` TEXT NOT NULL, `thumbnailUri` TEXT, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `location` TEXT NOT NULL, `latitude` REAL, `longitude` REAL, `takenAt` INTEGER NOT NULL, `uploadedAt` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, `tags` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `projects` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `clientName` TEXT NOT NULL, `clientPhone` TEXT NOT NULL, `address` TEXT NOT NULL, `startDate` INTEGER, `endDate` INTEGER, `status` TEXT NOT NULL, `budget` REAL NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `standard_rates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `rate` REAL NOT NULL, `unit` TEXT NOT NULL, `description` TEXT NOT NULL, `brand` TEXT NOT NULL, `isDefault` INTEGER NOT NULL, `location` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `rate_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `rateId` INTEGER NOT NULL, `oldRate` REAL NOT NULL, `newRate` REAL NOT NULL, `changedAt` INTEGER NOT NULL, `reason` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd5fa2bd8927beb8e475482a9cd8335d3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `calculations`");
        db.execSQL("DROP TABLE IF EXISTS `materials`");
        db.execSQL("DROP TABLE IF EXISTS `laborers`");
        db.execSQL("DROP TABLE IF EXISTS `attendance`");
        db.execSQL("DROP TABLE IF EXISTS `payments`");
        db.execSQL("DROP TABLE IF EXISTS `site_photos`");
        db.execSQL("DROP TABLE IF EXISTS `projects`");
        db.execSQL("DROP TABLE IF EXISTS `standard_rates`");
        db.execSQL("DROP TABLE IF EXISTS `rate_history`");
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
        final HashMap<String, TableInfo.Column> _columnsCalculations = new HashMap<String, TableInfo.Column>(15);
        _columnsCalculations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("projectName", new TableInfo.Column("projectName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("calculationType", new TableInfo.Column("calculationType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("length", new TableInfo.Column("length", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("width", new TableInfo.Column("width", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("height", new TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("area", new TableInfo.Column("area", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("materialCost", new TableInfo.Column("materialCost", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("laborCost", new TableInfo.Column("laborCost", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("totalCost", new TableInfo.Column("totalCost", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCalculations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCalculations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCalculations = new TableInfo("calculations", _columnsCalculations, _foreignKeysCalculations, _indicesCalculations);
        final TableInfo _existingCalculations = TableInfo.read(db, "calculations");
        if (!_infoCalculations.equals(_existingCalculations)) {
          return new RoomOpenHelper.ValidationResult(false, "calculations(com.nammamistri.app.data.model.Calculation).\n"
                  + " Expected:\n" + _infoCalculations + "\n"
                  + " Found:\n" + _existingCalculations);
        }
        final HashMap<String, TableInfo.Column> _columnsMaterials = new HashMap<String, TableInfo.Column>(7);
        _columnsMaterials.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterials.put("calculationId", new TableInfo.Column("calculationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterials.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterials.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterials.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterials.put("rate", new TableInfo.Column("rate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaterials.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMaterials = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMaterials = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMaterials = new TableInfo("materials", _columnsMaterials, _foreignKeysMaterials, _indicesMaterials);
        final TableInfo _existingMaterials = TableInfo.read(db, "materials");
        if (!_infoMaterials.equals(_existingMaterials)) {
          return new RoomOpenHelper.ValidationResult(false, "materials(com.nammamistri.app.data.model.Material).\n"
                  + " Expected:\n" + _infoMaterials + "\n"
                  + " Found:\n" + _existingMaterials);
        }
        final HashMap<String, TableInfo.Column> _columnsLaborers = new HashMap<String, TableInfo.Column>(10);
        _columnsLaborers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("skill", new TableInfo.Column("skill", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("dailyWage", new TableInfo.Column("dailyWage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaborers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLaborers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLaborers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLaborers = new TableInfo("laborers", _columnsLaborers, _foreignKeysLaborers, _indicesLaborers);
        final TableInfo _existingLaborers = TableInfo.read(db, "laborers");
        if (!_infoLaborers.equals(_existingLaborers)) {
          return new RoomOpenHelper.ValidationResult(false, "laborers(com.nammamistri.app.data.model.Labor).\n"
                  + " Expected:\n" + _infoLaborers + "\n"
                  + " Found:\n" + _existingLaborers);
        }
        final HashMap<String, TableInfo.Column> _columnsAttendance = new HashMap<String, TableInfo.Column>(10);
        _columnsAttendance.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("laborId", new TableInfo.Column("laborId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("projectName", new TableInfo.Column("projectName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("hoursWorked", new TableInfo.Column("hoursWorked", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("overtimeHours", new TableInfo.Column("overtimeHours", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("wageEarned", new TableInfo.Column("wageEarned", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("advancePaid", new TableInfo.Column("advancePaid", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAttendance = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAttendance = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAttendance = new TableInfo("attendance", _columnsAttendance, _foreignKeysAttendance, _indicesAttendance);
        final TableInfo _existingAttendance = TableInfo.read(db, "attendance");
        if (!_infoAttendance.equals(_existingAttendance)) {
          return new RoomOpenHelper.ValidationResult(false, "attendance(com.nammamistri.app.data.model.Attendance).\n"
                  + " Expected:\n" + _infoAttendance + "\n"
                  + " Found:\n" + _existingAttendance);
        }
        final HashMap<String, TableInfo.Column> _columnsPayments = new HashMap<String, TableInfo.Column>(7);
        _columnsPayments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("laborId", new TableInfo.Column("laborId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("paymentDate", new TableInfo.Column("paymentDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("paymentMode", new TableInfo.Column("paymentMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("isAdvance", new TableInfo.Column("isAdvance", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPayments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPayments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPayments = new TableInfo("payments", _columnsPayments, _foreignKeysPayments, _indicesPayments);
        final TableInfo _existingPayments = TableInfo.read(db, "payments");
        if (!_infoPayments.equals(_existingPayments)) {
          return new RoomOpenHelper.ValidationResult(false, "payments(com.nammamistri.app.data.model.Payment).\n"
                  + " Expected:\n" + _infoPayments + "\n"
                  + " Found:\n" + _existingPayments);
        }
        final HashMap<String, TableInfo.Column> _columnsSitePhotos = new HashMap<String, TableInfo.Column>(13);
        _columnsSitePhotos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("projectName", new TableInfo.Column("projectName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("photoUri", new TableInfo.Column("photoUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("thumbnailUri", new TableInfo.Column("thumbnailUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("takenAt", new TableInfo.Column("takenAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("uploadedAt", new TableInfo.Column("uploadedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSitePhotos.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSitePhotos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSitePhotos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSitePhotos = new TableInfo("site_photos", _columnsSitePhotos, _foreignKeysSitePhotos, _indicesSitePhotos);
        final TableInfo _existingSitePhotos = TableInfo.read(db, "site_photos");
        if (!_infoSitePhotos.equals(_existingSitePhotos)) {
          return new RoomOpenHelper.ValidationResult(false, "site_photos(com.nammamistri.app.data.model.SitePhoto).\n"
                  + " Expected:\n" + _infoSitePhotos + "\n"
                  + " Found:\n" + _existingSitePhotos);
        }
        final HashMap<String, TableInfo.Column> _columnsProjects = new HashMap<String, TableInfo.Column>(11);
        _columnsProjects.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("clientName", new TableInfo.Column("clientName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("clientPhone", new TableInfo.Column("clientPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("startDate", new TableInfo.Column("startDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("budget", new TableInfo.Column("budget", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProjects = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProjects = new TableInfo("projects", _columnsProjects, _foreignKeysProjects, _indicesProjects);
        final TableInfo _existingProjects = TableInfo.read(db, "projects");
        if (!_infoProjects.equals(_existingProjects)) {
          return new RoomOpenHelper.ValidationResult(false, "projects(com.nammamistri.app.data.model.Project).\n"
                  + " Expected:\n" + _infoProjects + "\n"
                  + " Found:\n" + _existingProjects);
        }
        final HashMap<String, TableInfo.Column> _columnsStandardRates = new HashMap<String, TableInfo.Column>(10);
        _columnsStandardRates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("rate", new TableInfo.Column("rate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("brand", new TableInfo.Column("brand", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("isDefault", new TableInfo.Column("isDefault", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStandardRates.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStandardRates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStandardRates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStandardRates = new TableInfo("standard_rates", _columnsStandardRates, _foreignKeysStandardRates, _indicesStandardRates);
        final TableInfo _existingStandardRates = TableInfo.read(db, "standard_rates");
        if (!_infoStandardRates.equals(_existingStandardRates)) {
          return new RoomOpenHelper.ValidationResult(false, "standard_rates(com.nammamistri.app.data.model.StandardRate).\n"
                  + " Expected:\n" + _infoStandardRates + "\n"
                  + " Found:\n" + _existingStandardRates);
        }
        final HashMap<String, TableInfo.Column> _columnsRateHistory = new HashMap<String, TableInfo.Column>(6);
        _columnsRateHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateHistory.put("rateId", new TableInfo.Column("rateId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateHistory.put("oldRate", new TableInfo.Column("oldRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateHistory.put("newRate", new TableInfo.Column("newRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateHistory.put("changedAt", new TableInfo.Column("changedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateHistory.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRateHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRateHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRateHistory = new TableInfo("rate_history", _columnsRateHistory, _foreignKeysRateHistory, _indicesRateHistory);
        final TableInfo _existingRateHistory = TableInfo.read(db, "rate_history");
        if (!_infoRateHistory.equals(_existingRateHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "rate_history(com.nammamistri.app.data.model.RateHistory).\n"
                  + " Expected:\n" + _infoRateHistory + "\n"
                  + " Found:\n" + _existingRateHistory);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d5fa2bd8927beb8e475482a9cd8335d3", "b09f442b412aa34091086b0503d49f23");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "calculations","materials","laborers","attendance","payments","site_photos","projects","standard_rates","rate_history");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `calculations`");
      _db.execSQL("DELETE FROM `materials`");
      _db.execSQL("DELETE FROM `laborers`");
      _db.execSQL("DELETE FROM `attendance`");
      _db.execSQL("DELETE FROM `payments`");
      _db.execSQL("DELETE FROM `site_photos`");
      _db.execSQL("DELETE FROM `projects`");
      _db.execSQL("DELETE FROM `standard_rates`");
      _db.execSQL("DELETE FROM `rate_history`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
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
    _typeConvertersMap.put(CalculationDao.class, CalculationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LaborDao.class, LaborDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PhotoDao.class, PhotoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RatesDao.class, RatesDao_Impl.getRequiredConverters());
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
  public CalculationDao calculationDao() {
    if (_calculationDao != null) {
      return _calculationDao;
    } else {
      synchronized(this) {
        if(_calculationDao == null) {
          _calculationDao = new CalculationDao_Impl(this);
        }
        return _calculationDao;
      }
    }
  }

  @Override
  public LaborDao laborDao() {
    if (_laborDao != null) {
      return _laborDao;
    } else {
      synchronized(this) {
        if(_laborDao == null) {
          _laborDao = new LaborDao_Impl(this);
        }
        return _laborDao;
      }
    }
  }

  @Override
  public PhotoDao photoDao() {
    if (_photoDao != null) {
      return _photoDao;
    } else {
      synchronized(this) {
        if(_photoDao == null) {
          _photoDao = new PhotoDao_Impl(this);
        }
        return _photoDao;
      }
    }
  }

  @Override
  public RatesDao ratesDao() {
    if (_ratesDao != null) {
      return _ratesDao;
    } else {
      synchronized(this) {
        if(_ratesDao == null) {
          _ratesDao = new RatesDao_Impl(this);
        }
        return _ratesDao;
      }
    }
  }
}
