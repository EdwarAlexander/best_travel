db.createUser({
        user: 'root',
        pwd: 'toor',
        roles: [
            {
                role: 'readWrite',
                db: 'testDB',
            },
        ],
    });
db.createCollection('app_users', { capped: false });

db.app_users.insert([
    {
        "username": "ragnar777",
        "dni": "VIKI771012HMCRG093",
        "enabled": true,
        "password_no_secret": "s3cr3t",
        "password": "$2a$10$jJSqMm9pbINLHPqHN1smxuDQWpKyY/00Av0vlit7710hK4ch5mcV2",
        "role":
        {
            "granted_authorities": ["ROLE_USER"]
        }
    },
    {
        "username": "heisenberg",
        "dni": "BBMB771012HMCRR022",
        "enabled": true,
        "password_no_secret": "p4sw0rd",
        "password": "$2a$10$bOE6tB/wGK2y0UuTTK0jKuYQ253xcc4V5zXciH5BKqzYr8j7vxrUu",
        "role":
        {
            "granted_authorities": ["ROLE_USER"]
        }
    },
    {
        "username": "misterX",
        "dni": "GOTW771012HMRGR087",
        "enabled": true,
        "password_no_secret": "misterX123",
        "password": "$2a$10$E9OyYUix7nybCO3pj1/.xuX8dp8mL1qeOfMoquzk7iMdrldBWnVjO",
        "role":
        {
            "granted_authorities": ["ROLE_USER", "ROLE_ADMIN"]
        }
    },
    {
        "username": "neverMore",
        "dni": "WALA771012HCRGR054",
        "enabled": true,
        "password_no_secret": "4dmIn",
        "password": "$2a$10$dRjTEpkIiEHX.jAkOFLCjOy7mLystdp/QcG7okIVjj3K8gd25mIKy",
        "role":
        {
            "granted_authorities": ["ROLE_ADMIN"]
        }
    }
]);