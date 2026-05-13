package com.nammamistri.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nammamistri.app.data.model.Attendance;
import com.nammamistri.app.data.model.AttendanceStatus;
import com.nammamistri.app.data.model.Labor;
import com.nammamistri.app.data.model.LaborSkill;
import com.nammamistri.app.data.model.Payment;
import com.nammamistri.app.data.model.PaymentMode;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LaborDao_Impl implements LaborDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Labor> __insertionAdapterOfLabor;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<Attendance> __insertionAdapterOfAttendance;

  private final EntityInsertionAdapter<Payment> __insertionAdapterOfPayment;

  private final EntityDeletionOrUpdateAdapter<Labor> __deletionAdapterOfLabor;

  private final EntityDeletionOrUpdateAdapter<Attendance> __deletionAdapterOfAttendance;

  private final EntityDeletionOrUpdateAdapter<Payment> __deletionAdapterOfPayment;

  private final EntityDeletionOrUpdateAdapter<Labor> __updateAdapterOfLabor;

  private final EntityDeletionOrUpdateAdapter<Attendance> __updateAdapterOfAttendance;

  private final EntityDeletionOrUpdateAdapter<Payment> __updateAdapterOfPayment;

  public LaborDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLabor = new EntityInsertionAdapter<Labor>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `laborers` (`id`,`name`,`phone`,`skill`,`dailyWage`,`isActive`,`photoUri`,`address`,`notes`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Labor entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhone());
        final String _tmp = __converters.fromLaborSkill(entity.getSkill());
        statement.bindString(4, _tmp);
        statement.bindDouble(5, entity.getDailyWage());
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        if (entity.getPhotoUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPhotoUri());
        }
        statement.bindString(8, entity.getAddress());
        statement.bindString(9, entity.getNotes());
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfAttendance = new EntityInsertionAdapter<Attendance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `attendance` (`id`,`laborId`,`date`,`projectName`,`hoursWorked`,`overtimeHours`,`status`,`wageEarned`,`advancePaid`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Attendance entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLaborId());
        statement.bindLong(3, entity.getDate());
        statement.bindString(4, entity.getProjectName());
        statement.bindDouble(5, entity.getHoursWorked());
        statement.bindDouble(6, entity.getOvertimeHours());
        final String _tmp = __converters.fromAttendanceStatus(entity.getStatus());
        statement.bindString(7, _tmp);
        statement.bindDouble(8, entity.getWageEarned());
        statement.bindDouble(9, entity.getAdvancePaid());
        statement.bindString(10, entity.getNotes());
      }
    };
    this.__insertionAdapterOfPayment = new EntityInsertionAdapter<Payment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `payments` (`id`,`laborId`,`amount`,`paymentDate`,`paymentMode`,`description`,`isAdvance`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Payment entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLaborId());
        statement.bindDouble(3, entity.getAmount());
        statement.bindLong(4, entity.getPaymentDate());
        final String _tmp = __converters.fromPaymentMode(entity.getPaymentMode());
        statement.bindString(5, _tmp);
        statement.bindString(6, entity.getDescription());
        final int _tmp_1 = entity.isAdvance() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
      }
    };
    this.__deletionAdapterOfLabor = new EntityDeletionOrUpdateAdapter<Labor>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `laborers` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Labor entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfAttendance = new EntityDeletionOrUpdateAdapter<Attendance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `attendance` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Attendance entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfPayment = new EntityDeletionOrUpdateAdapter<Payment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `payments` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Payment entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfLabor = new EntityDeletionOrUpdateAdapter<Labor>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `laborers` SET `id` = ?,`name` = ?,`phone` = ?,`skill` = ?,`dailyWage` = ?,`isActive` = ?,`photoUri` = ?,`address` = ?,`notes` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Labor entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhone());
        final String _tmp = __converters.fromLaborSkill(entity.getSkill());
        statement.bindString(4, _tmp);
        statement.bindDouble(5, entity.getDailyWage());
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        if (entity.getPhotoUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPhotoUri());
        }
        statement.bindString(8, entity.getAddress());
        statement.bindString(9, entity.getNotes());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__updateAdapterOfAttendance = new EntityDeletionOrUpdateAdapter<Attendance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `attendance` SET `id` = ?,`laborId` = ?,`date` = ?,`projectName` = ?,`hoursWorked` = ?,`overtimeHours` = ?,`status` = ?,`wageEarned` = ?,`advancePaid` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Attendance entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLaborId());
        statement.bindLong(3, entity.getDate());
        statement.bindString(4, entity.getProjectName());
        statement.bindDouble(5, entity.getHoursWorked());
        statement.bindDouble(6, entity.getOvertimeHours());
        final String _tmp = __converters.fromAttendanceStatus(entity.getStatus());
        statement.bindString(7, _tmp);
        statement.bindDouble(8, entity.getWageEarned());
        statement.bindDouble(9, entity.getAdvancePaid());
        statement.bindString(10, entity.getNotes());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__updateAdapterOfPayment = new EntityDeletionOrUpdateAdapter<Payment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `payments` SET `id` = ?,`laborId` = ?,`amount` = ?,`paymentDate` = ?,`paymentMode` = ?,`description` = ?,`isAdvance` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Payment entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLaborId());
        statement.bindDouble(3, entity.getAmount());
        statement.bindLong(4, entity.getPaymentDate());
        final String _tmp = __converters.fromPaymentMode(entity.getPaymentMode());
        statement.bindString(5, _tmp);
        statement.bindString(6, entity.getDescription());
        final int _tmp_1 = entity.isAdvance() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insertLabor(final Labor labor, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLabor.insertAndReturnId(labor);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAttendance(final Attendance attendance,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAttendance.insertAndReturnId(attendance);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAttendances(final List<Attendance> attendances,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAttendance.insert(attendances);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPayment(final Payment payment, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPayment.insertAndReturnId(payment);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLabor(final Labor labor, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfLabor.handle(labor);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAttendance(final Attendance attendance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAttendance.handle(attendance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePayment(final Payment payment, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPayment.handle(payment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLabor(final Labor labor, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLabor.handle(labor);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAttendance(final Attendance attendance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAttendance.handle(attendance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePayment(final Payment payment, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPayment.handle(payment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Labor>> getAllActiveLaborers() {
    final String _sql = "SELECT * FROM laborers WHERE isActive = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"laborers"}, new Callable<List<Labor>>() {
      @Override
      @NonNull
      public List<Labor> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfSkill = CursorUtil.getColumnIndexOrThrow(_cursor, "skill");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Labor> _result = new ArrayList<Labor>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Labor _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final LaborSkill _tmpSkill;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkill);
            _tmpSkill = __converters.toLaborSkill(_tmp);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Labor(_tmpId,_tmpName,_tmpPhone,_tmpSkill,_tmpDailyWage,_tmpIsActive,_tmpPhotoUri,_tmpAddress,_tmpNotes,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Labor>> getAllLaborers() {
    final String _sql = "SELECT * FROM laborers ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"laborers"}, new Callable<List<Labor>>() {
      @Override
      @NonNull
      public List<Labor> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfSkill = CursorUtil.getColumnIndexOrThrow(_cursor, "skill");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Labor> _result = new ArrayList<Labor>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Labor _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final LaborSkill _tmpSkill;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkill);
            _tmpSkill = __converters.toLaborSkill(_tmp);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Labor(_tmpId,_tmpName,_tmpPhone,_tmpSkill,_tmpDailyWage,_tmpIsActive,_tmpPhotoUri,_tmpAddress,_tmpNotes,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Labor>> getAllLaborersLiveData() {
    final String _sql = "SELECT * FROM laborers ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"laborers"}, false, new Callable<List<Labor>>() {
      @Override
      @Nullable
      public List<Labor> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfSkill = CursorUtil.getColumnIndexOrThrow(_cursor, "skill");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Labor> _result = new ArrayList<Labor>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Labor _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final LaborSkill _tmpSkill;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkill);
            _tmpSkill = __converters.toLaborSkill(_tmp);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Labor(_tmpId,_tmpName,_tmpPhone,_tmpSkill,_tmpDailyWage,_tmpIsActive,_tmpPhotoUri,_tmpAddress,_tmpNotes,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLaborById(final long id, final Continuation<? super Labor> $completion) {
    final String _sql = "SELECT * FROM laborers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Labor>() {
      @Override
      @Nullable
      public Labor call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfSkill = CursorUtil.getColumnIndexOrThrow(_cursor, "skill");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Labor _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final LaborSkill _tmpSkill;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkill);
            _tmpSkill = __converters.toLaborSkill(_tmp);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Labor(_tmpId,_tmpName,_tmpPhone,_tmpSkill,_tmpDailyWage,_tmpIsActive,_tmpPhotoUri,_tmpAddress,_tmpNotes,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Labor> getLaborByIdLiveData(final long id) {
    final String _sql = "SELECT * FROM laborers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"laborers"}, false, new Callable<Labor>() {
      @Override
      @Nullable
      public Labor call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfSkill = CursorUtil.getColumnIndexOrThrow(_cursor, "skill");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Labor _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final LaborSkill _tmpSkill;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkill);
            _tmpSkill = __converters.toLaborSkill(_tmp);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Labor(_tmpId,_tmpName,_tmpPhone,_tmpSkill,_tmpDailyWage,_tmpIsActive,_tmpPhotoUri,_tmpAddress,_tmpNotes,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Labor>> getLaborersBySkill(final LaborSkill skill) {
    final String _sql = "SELECT * FROM laborers WHERE skill = ? AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromLaborSkill(skill);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"laborers"}, new Callable<List<Labor>>() {
      @Override
      @NonNull
      public List<Labor> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfSkill = CursorUtil.getColumnIndexOrThrow(_cursor, "skill");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Labor> _result = new ArrayList<Labor>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Labor _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final LaborSkill _tmpSkill;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSkill);
            _tmpSkill = __converters.toLaborSkill(_tmp_1);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Labor(_tmpId,_tmpName,_tmpPhone,_tmpSkill,_tmpDailyWage,_tmpIsActive,_tmpPhotoUri,_tmpAddress,_tmpNotes,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Labor>> searchLaborers(final String query) {
    final String _sql = "SELECT * FROM laborers WHERE name LIKE '%' || ? || '%' OR phone LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"laborers"}, new Callable<List<Labor>>() {
      @Override
      @NonNull
      public List<Labor> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfSkill = CursorUtil.getColumnIndexOrThrow(_cursor, "skill");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Labor> _result = new ArrayList<Labor>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Labor _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final LaborSkill _tmpSkill;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkill);
            _tmpSkill = __converters.toLaborSkill(_tmp);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Labor(_tmpId,_tmpName,_tmpPhone,_tmpSkill,_tmpDailyWage,_tmpIsActive,_tmpPhotoUri,_tmpAddress,_tmpNotes,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Attendance>> getAttendanceByLabor(final long laborId) {
    final String _sql = "SELECT * FROM attendance WHERE laborId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"attendance"}, new Callable<List<Attendance>>() {
      @Override
      @NonNull
      public List<Attendance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLaborId = CursorUtil.getColumnIndexOrThrow(_cursor, "laborId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfHoursWorked = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursWorked");
          final int _cursorIndexOfOvertimeHours = CursorUtil.getColumnIndexOrThrow(_cursor, "overtimeHours");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfWageEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "wageEarned");
          final int _cursorIndexOfAdvancePaid = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePaid");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Attendance> _result = new ArrayList<Attendance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Attendance _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLaborId;
            _tmpLaborId = _cursor.getLong(_cursorIndexOfLaborId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final double _tmpHoursWorked;
            _tmpHoursWorked = _cursor.getDouble(_cursorIndexOfHoursWorked);
            final double _tmpOvertimeHours;
            _tmpOvertimeHours = _cursor.getDouble(_cursorIndexOfOvertimeHours);
            final AttendanceStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toAttendanceStatus(_tmp);
            final double _tmpWageEarned;
            _tmpWageEarned = _cursor.getDouble(_cursorIndexOfWageEarned);
            final double _tmpAdvancePaid;
            _tmpAdvancePaid = _cursor.getDouble(_cursorIndexOfAdvancePaid);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Attendance(_tmpId,_tmpLaborId,_tmpDate,_tmpProjectName,_tmpHoursWorked,_tmpOvertimeHours,_tmpStatus,_tmpWageEarned,_tmpAdvancePaid,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Attendance>> getAttendanceByDate(final long date) {
    final String _sql = "SELECT * FROM attendance WHERE date = ? ORDER BY laborId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"attendance"}, new Callable<List<Attendance>>() {
      @Override
      @NonNull
      public List<Attendance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLaborId = CursorUtil.getColumnIndexOrThrow(_cursor, "laborId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfHoursWorked = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursWorked");
          final int _cursorIndexOfOvertimeHours = CursorUtil.getColumnIndexOrThrow(_cursor, "overtimeHours");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfWageEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "wageEarned");
          final int _cursorIndexOfAdvancePaid = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePaid");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Attendance> _result = new ArrayList<Attendance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Attendance _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLaborId;
            _tmpLaborId = _cursor.getLong(_cursorIndexOfLaborId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final double _tmpHoursWorked;
            _tmpHoursWorked = _cursor.getDouble(_cursorIndexOfHoursWorked);
            final double _tmpOvertimeHours;
            _tmpOvertimeHours = _cursor.getDouble(_cursorIndexOfOvertimeHours);
            final AttendanceStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toAttendanceStatus(_tmp);
            final double _tmpWageEarned;
            _tmpWageEarned = _cursor.getDouble(_cursorIndexOfWageEarned);
            final double _tmpAdvancePaid;
            _tmpAdvancePaid = _cursor.getDouble(_cursorIndexOfAdvancePaid);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Attendance(_tmpId,_tmpLaborId,_tmpDate,_tmpProjectName,_tmpHoursWorked,_tmpOvertimeHours,_tmpStatus,_tmpWageEarned,_tmpAdvancePaid,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Attendance>> getAttendanceInRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM attendance WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"attendance"}, new Callable<List<Attendance>>() {
      @Override
      @NonNull
      public List<Attendance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLaborId = CursorUtil.getColumnIndexOrThrow(_cursor, "laborId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfHoursWorked = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursWorked");
          final int _cursorIndexOfOvertimeHours = CursorUtil.getColumnIndexOrThrow(_cursor, "overtimeHours");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfWageEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "wageEarned");
          final int _cursorIndexOfAdvancePaid = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePaid");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Attendance> _result = new ArrayList<Attendance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Attendance _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLaborId;
            _tmpLaborId = _cursor.getLong(_cursorIndexOfLaborId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final double _tmpHoursWorked;
            _tmpHoursWorked = _cursor.getDouble(_cursorIndexOfHoursWorked);
            final double _tmpOvertimeHours;
            _tmpOvertimeHours = _cursor.getDouble(_cursorIndexOfOvertimeHours);
            final AttendanceStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toAttendanceStatus(_tmp);
            final double _tmpWageEarned;
            _tmpWageEarned = _cursor.getDouble(_cursorIndexOfWageEarned);
            final double _tmpAdvancePaid;
            _tmpAdvancePaid = _cursor.getDouble(_cursorIndexOfAdvancePaid);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Attendance(_tmpId,_tmpLaborId,_tmpDate,_tmpProjectName,_tmpHoursWorked,_tmpOvertimeHours,_tmpStatus,_tmpWageEarned,_tmpAdvancePaid,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAttendanceForLaborOnDate(final long laborId, final long date,
      final Continuation<? super Attendance> $completion) {
    final String _sql = "SELECT * FROM attendance WHERE laborId = ? AND date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, date);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Attendance>() {
      @Override
      @Nullable
      public Attendance call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLaborId = CursorUtil.getColumnIndexOrThrow(_cursor, "laborId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfHoursWorked = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursWorked");
          final int _cursorIndexOfOvertimeHours = CursorUtil.getColumnIndexOrThrow(_cursor, "overtimeHours");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfWageEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "wageEarned");
          final int _cursorIndexOfAdvancePaid = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePaid");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final Attendance _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLaborId;
            _tmpLaborId = _cursor.getLong(_cursorIndexOfLaborId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final double _tmpHoursWorked;
            _tmpHoursWorked = _cursor.getDouble(_cursorIndexOfHoursWorked);
            final double _tmpOvertimeHours;
            _tmpOvertimeHours = _cursor.getDouble(_cursorIndexOfOvertimeHours);
            final AttendanceStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toAttendanceStatus(_tmp);
            final double _tmpWageEarned;
            _tmpWageEarned = _cursor.getDouble(_cursorIndexOfWageEarned);
            final double _tmpAdvancePaid;
            _tmpAdvancePaid = _cursor.getDouble(_cursorIndexOfAdvancePaid);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new Attendance(_tmpId,_tmpLaborId,_tmpDate,_tmpProjectName,_tmpHoursWorked,_tmpOvertimeHours,_tmpStatus,_tmpWageEarned,_tmpAdvancePaid,_tmpNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalWagesEarnedByLabor(final long laborId,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(wageEarned) FROM attendance WHERE laborId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getWagesInRange(final long laborId, final long startDate, final long endDate,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(wageEarned) FROM attendance WHERE laborId = ? AND date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endDate);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAttendanceCountByStatus(final long laborId, final AttendanceStatus status,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM attendance WHERE laborId = ? AND status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    _argIndex = 2;
    final String _tmp = __converters.fromAttendanceStatus(status);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(0);
            _result = _tmp_1;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Payment>> getPaymentsByLabor(final long laborId) {
    final String _sql = "SELECT * FROM payments WHERE laborId = ? ORDER BY paymentDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"payments"}, new Callable<List<Payment>>() {
      @Override
      @NonNull
      public List<Payment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLaborId = CursorUtil.getColumnIndexOrThrow(_cursor, "laborId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfPaymentMode = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMode");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsAdvance = CursorUtil.getColumnIndexOrThrow(_cursor, "isAdvance");
          final List<Payment> _result = new ArrayList<Payment>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Payment _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLaborId;
            _tmpLaborId = _cursor.getLong(_cursorIndexOfLaborId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final long _tmpPaymentDate;
            _tmpPaymentDate = _cursor.getLong(_cursorIndexOfPaymentDate);
            final PaymentMode _tmpPaymentMode;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfPaymentMode);
            _tmpPaymentMode = __converters.toPaymentMode(_tmp);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsAdvance;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAdvance);
            _tmpIsAdvance = _tmp_1 != 0;
            _item = new Payment(_tmpId,_tmpLaborId,_tmpAmount,_tmpPaymentDate,_tmpPaymentMode,_tmpDescription,_tmpIsAdvance);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTotalPaymentsByLabor(final long laborId,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(amount) FROM payments WHERE laborId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalAdvanceByLabor(final long laborId,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(amount) FROM payments WHERE laborId = ? AND isAdvance = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLaborBalance(final long laborId,
      final Continuation<? super Double> $completion) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            COALESCE((SELECT SUM(wageEarned) FROM attendance WHERE laborId = ?), 0) -\n"
            + "            COALESCE((SELECT SUM(amount) FROM payments WHERE laborId = ?), 0)\n"
            + "        AS balance\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, laborId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, laborId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
