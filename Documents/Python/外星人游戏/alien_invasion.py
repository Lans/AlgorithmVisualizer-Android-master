import pygame
# 导入外部py文件
from setting import Settings
from ship import Ship
from love import Love
# 导入外部方法
import game_functions as gf


# 定义开始游戏方法
def run_game():
    # 初始化游戏并创建屏幕对象
    pygame.init()
    ai_settings = Settings()
    scree = pygame.display.set_mode(
        (ai_settings.scree_width, ai_settings.scree_height))
    pygame.display.set_caption("Alien Invasion")

    # 绘制飞船
    ship = Ship(scree, ai_settings)
    love = Love(scree)
    # 开始游戏
    while True:
        gf.check_events(ship)
        ship.update()
        gf.update_scree(ai_settings, scree, ship, love)


run_game()
