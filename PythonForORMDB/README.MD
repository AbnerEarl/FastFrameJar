
# Automated orm framework:

1. Map the database according to the entity class;

2. It has its own method of adding, deleting, modifying and checking, and each method can set multiple conditions;

3. Complete the operation on the database without writing sql;

4. Support multiple database types, including mainstream relational database, non-relational database, middleware storage, etc.;

5. Free and open source, continuous improvement and update.

# How to use?

It's so easy, it only takes three steps.

### step 1:

Install the library:
```buildoutcfg
pip install ormdb
```
### step 2:

create a object:

```buildoutcfg
#!/usr/bin/env python
# coding: utf-8
# author: Frank YCJ
# email: 1320259466@qq.com
from ormdb.Field import IntegerField
from ormdb.Model import Model
from ormdb.Field import StringField


class User(Model):
    # Define the mapping of class attributes to columns：
    id = IntegerField('id')
    name = StringField('username')
    email = StringField('email')
    password = StringField('password')

```

### step 3:

Perform relevant operations on the data:

```buildoutcfg
#!/usr/bin/env python
# coding: utf-8
# author: Frank YCJ
# email: 1320259466@qq.com

from ormdb.LogLevel import LogLevel
from ormdb import Config
# Set log level
Config.set_log_level(LogLevel.All.value)
from ormdb.User import User


def saveData():
    # Create an instance：
    u = User(id=12345, name='Frank', email='aaabbbccc@outlook.com', password='111111')
    # Save to database
    u.save()
    u.saveOrUpdate()
    ......
    # Query data
    u.search(id=12345)
    u.search(id=12345).ors(name="Frank")
    u.like(name,"Frank")
    u.search(name="Frank").orderby(name)
    ......
    # change the data
    u.update()
    u.update(id=12345,name='Swollow')
    ......
    # delete data
    u.delete()
    u.delete(id=12345)
    ......

saveData()
```


# More

We are working hard to perfect...

# Suggestion

https://github.com/YouAreOnlyOne/FastFrameJar/issues