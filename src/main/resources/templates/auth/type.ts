export type randomPhotoState = {
    alt_description: string;
    blur_hash: string;
    color: string;
    created_at: string;
    description: string;
    downloads: number;
    id: string;
    liked_by_user: boolean;
    likes: number;
    links: {
        // 생략
    };
    location: {
        // 생략
    };
    promoted_at: string;
    sponsorship: string;
    updated_at: string;
    urls: {
        // 생략
    };
    user: {
        // 생략
        profile_image: {
            // 생략
        };
        username: string;
    };
    view: number;
    width: number;
    height: number;
};

export type searchUserState = {
    total: number;
    total_pages: number;
    results: [searchUserResults];
};

export type searchUserResults = {
    id: string;
    updated_at: string;
    username: string;
    name: string;
    first_name: string;
    last_name: string;
    bio: string;
    location: string;
    links: {
        // 생략
    };
    profile_image: {
        // 생략
    };
    social: {
        instagram_uesrname: string;
    };
    photos: [
        {
            // 생략
            urls: {
                // 생략
            };
        },
    ];
};